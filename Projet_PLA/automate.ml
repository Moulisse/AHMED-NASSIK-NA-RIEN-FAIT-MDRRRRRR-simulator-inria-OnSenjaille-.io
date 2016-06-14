(* TYPES *)
type cellule =
  | C
  | N (* nord *) | S | E | O;;

type action =
  | Na
  | Avancer of cellule 
  | Frapper of cellule
  | Peindre_R of cellule
  | Peindre_B of cellule
  | Attendre
;;

type condition =
  | EnnemiEloigne of cellule 
  | Ennemi of cellule 
  | Allie of cellule 
  | CaseBlancheEloignee of cellule 
  | CaseRougeEloignee of cellule 
  | CaseBleuEloignee of cellule 
  | CaseBlanche of cellule
  | CaseRouge of cellule
  | CaseBleu of cellule
  | Mur of cellule ;;

type etat = int;;
type transition = etat * condition * action * etat;;
type automate = transition list;;

type personnage=
  | Guerrier
  | Peintre;;

(* TRADUCTION DES CONDITIONS COMPLEXES EN ENTIER *)
  
let (cellule_to_int: cellule -> int) = function
  | C -> 4
  | N -> 0
  | S -> 2
  | E -> 1
  | O -> 3;;

let (condition_to_int: condition -> int) =  function
  | EnnemiEloigne (cellule) -> 1+(cellule_to_int cellule )
  | Ennemi (cellule) -> 5+(cellule_to_int cellule )
  | Allie (cellule) -> 9+(cellule_to_int cellule )
  | CaseBlancheEloignee (cellule) -> 13+(cellule_to_int cellule )
  | CaseRougeEloignee (cellule) -> 17+(cellule_to_int cellule )
  | CaseBleuEloignee (cellule) -> 21+(cellule_to_int cellule )
  | CaseBlanche (cellule) -> 25+(cellule_to_int cellule )
  | CaseRouge (cellule) -> 29+(cellule_to_int cellule )
  | CaseBleu (cellule) -> 33+(cellule_to_int cellule )
  | Mur (cellule) -> 37+(cellule_to_int cellule )
;;


let (action_to_int: action -> int) = function
 | Na -> 0
 | Avancer (cellule) -> 41 + (cellule_to_int cellule)
 | Frapper (cellule) -> 45 + (cellule_to_int cellule)
 | Peindre_R (cellule) -> 49 + (cellule_to_int cellule)
 | Peindre_B (cellule) -> 54 + (cellule_to_int cellule)
 | Attendre -> 59
;;
  
let (traduction_transition: transition -> int*int*int*int) = fun
    (src,condition,action,tgt) -> (src, condition_to_int condition, action_to_int action, tgt);;

let traduction_automate ((a,p):(automate * personnage))=
  (List.map traduction_transition a,p);;

let rec trad_liste_aut (l:(automate * personnage) list)=
  List.map traduction_automate l;;

  
let convert (p:personnage)=match p with
  |Guerrier -> "guerrier"
  |Peintre ->"peintre";;
  
(* FONCTIONS AUXILIAIRES *)
let rec add_int (i:int)(l:int list)=
  match l with
  |e::fin->if e=i then e::fin
	   else e::add_int i fin
  |[]->[i];;
  
let rec compte_lc_aux (a:(int*int*int*int) list) ((conds,etats):int list*int list):(int*int)= match a with
  |(e1,cond,act,e2)::fin-> compte_lc_aux fin ((add_int cond conds),(let etats=add_int e1 etats in add_int e2 etats)) 
  |[]->(List.length conds,List.length etats);;

let compte_lc (a:(int*int*int*int) list)= compte_lc_aux a ([],[]);;
  
let associe (a:'a list) (p: personnage)=(a,p);;
  
let rec completer_aux (act:int) (l:(int*int*int*int) list) ((nb_c,nb_l):(int*int)) ((col,lig):(int*int))=
  match l with
  |(e1,cond,act1,e2)::fin->if col=(nb_c-1)&& lig<nb_l
			   then
			     if (e1=nb_c && (lig)=cond)
			     then (e1,cond,act1,e2)::(completer_aux act fin (nb_c,nb_l) (0,lig+1))
			     else (e1,cond,act1,e2)::(nb_c-1,cond,act,nb_c-1)::(completer_aux act fin (nb_c,nb_l) (0,lig+1))
			   else if lig=nb_l
			   then failwith "probleme dans le nb_l"
			   else
			     if (e1=nb_c && (lig)=cond)
			     then (e1,cond,act1,e2)::(completer_aux act fin (nb_c,nb_l) (col+1,lig))
			     else (e1,cond,act1,e2)::(col,lig,act,col)::(completer_aux act fin (nb_c,nb_l) (col+1,lig))
  |[]->[];;
  
let completer (act:int) ((l,p):(int*int*int*int) list*personnage) ((nb_c,nb_l):(int*int)):((int*int*int*int) list*personnage)=
  (completer_aux act l (nb_c,nb_l) (0,0),p)
;;
  
(* TRI *)

let rec decoupe ((e1,cond,act1,e2) : (int*int*int*int))(l:(int*int*int*int) list):((int*int*int*int) list*(int*int*int*int) list)=
  match l with
  | [] -> ([], [])
  | (e3,cond2,act2,e4)::t -> let (l1, l2)=decoupe (e1,cond,act1,e2) t in
			     if cond2 < cond
			     then ((e3,cond2,act2,e4)::l1, l2)
			     else if (cond2 = cond && e3<e1)
			     then ((e3,cond2,act2,e4)::l1, l2)
			     else (l1, (e3,cond2,act2,e4)::l2) ;;
  
let rec tri_rapide (l: (int * int * int * int) list)=
  match l with 
  | [] -> []
  | h::t -> let (l1, l2)=decoupe h t in 
	    (tri_rapide l1)@h::(tri_rapide l2) ;;

let tri_aut ((l,p):(int*int*int*int)list*personnage) =
  (tri_rapide l,p);;

let tri_list_aut (l:((int*int*int*int)list*personnage)list)=
  List.map tri_aut l;;
(* FONCTIONS ECRITURE XML *)
let write_entete (fichier:out_channel)=
  Printf.fprintf fichier "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";;

let write_nb_aut (fichier:out_channel) (nb:string)=
  Printf.fprintf fichier "\n\t<nb_aut>%s</nb_aut>\n" nb;;

let write_act_entete (fichier:out_channel) (a:(int*int*int*int) list) ((l,c):(int*int))=
  Printf.fprintf fichier "\t\t<action nb_l=\"%d\" nb_c=\"%d\">\n" l c;;

let write_tr_entete (fichier:out_channel) (a:(int*int*int*int) list) ((l,c):(int*int))=
  Printf.fprintf fichier "\t\t<transition nb_l=\"%d\" nb_c=\"%d\">\n" l c;;
  
let rec write_act (f:out_channel) (a:(int*int*int*int) list) (l:int) (debut:bool)=
  match a with
  |(e1,cond,act1,e2)::fin-> if debut
			    then
			      let ()=Printf.fprintf f "\t\t\t<l>\n"
			      and ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int act1)
			      in write_act f fin cond false
			    else if l=cond
			    then
			      let ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int act1) 
			      in write_act f fin cond false
			    else 
			      let ()=Printf.fprintf f "\t\t\t</l>\n" 
			      and ()=Printf.fprintf f "\t\t\t<l>\n" 
			      and  ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int act1)
			      in write_act f fin cond false
  |[]-> let ()=Printf.fprintf f "\t\t\t</l>\n" 
	in Printf.fprintf f "\t\t</action>\n" ;;
  
let rec write_tr (f:out_channel) (a:(int*int*int*int) list) (l:int) (debut:bool)=
  match a with
  |(e1,cond,act1,e2)::fin-> if debut
			    then
			      let ()=Printf.fprintf f "\t\t\t<l>\n"
			      and ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int e2)
			      in write_tr f fin cond false
			    else if l=cond
			    then
			      let ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int e2) 
			      in write_tr f fin cond false
			    else 
			      let ()=Printf.fprintf f "\t\t\t</l>\n" 
			      and ()=Printf.fprintf f "\t\t\t<l>\n" 
			      and ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int e2)
			      in write_tr f fin cond false
  |[]-> let ()=Printf.fprintf f "\t\t\t</l>\n" 
	in Printf.fprintf f "\t\t</transition>\n" ;;

let rec write_aut (fichier:out_channel) (list: ((int * int * int * int) list * personnage) list) (act:int) =
  match list with
  |(a,p)::fin->let ()=Printf.fprintf fichier "\t<au personnage=\"%s\">\n\n" (convert p)
	       and (l,c)=compte_lc a
	       in let (a,p)= completer act (a,p) (l,c)
		  in let()=write_act_entete fichier a (l,c)
		     and ()=write_act fichier a 0 true
		     and ()=write_tr_entete fichier a (l,c)
		     and ()=write_tr fichier a 0 true
		     and ()=Printf.fprintf fichier "\t</au>\n\n"
		     in write_aut fichier fin act
  |[]->();;
  
  
let write_xml (fichier:out_channel) (list: (automate * personnage) list) (act:int)=
  let list_trad =tri_list_aut (trad_liste_aut list)
  in let ()=write_entete fichier
     and ()=Printf.fprintf fichier "<automates>\n\n"
     and ()=write_nb_aut fichier (string_of_int (List.length list))
     and ()=write_aut fichier list_trad act
     in Printf.fprintf fichier "</automates>";;    

(* AUTOMATES *)

(*  
let aut1 =
  ([(1, Ennemi,Frapper, 1)]
  ,Guerrier);;


(*
let aut2 =
  ([(0,A_peindre,Peindre,1);
    (1,Peinte,Avancer,0)],
   Peintre);;
 *)
let test=[aut1];;
 *)  
(* TEST *)

let fichier = open_out "test1.xml";;
let () = write_xml fichier test 1;;
  close_out fichier;;

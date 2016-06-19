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
  | Allie (cellule) -> 8+(cellule_to_int cellule )
  | CaseBlancheEloignee (cellule) -> 12+(cellule_to_int cellule )
  | CaseRougeEloignee (cellule) -> 16+(cellule_to_int cellule )
  | CaseBleuEloignee (cellule) -> 20+(cellule_to_int cellule )
  | CaseBlanche (cellule) -> 24+(cellule_to_int cellule )
  | CaseRouge (cellule) -> 29+(cellule_to_int cellule )
  | CaseBleu (cellule) -> 34+(cellule_to_int cellule )
  | Mur (cellule) -> 38+(cellule_to_int cellule )
;;


let (action_to_int: action -> int) = function
 | Na -> 0
 | Avancer (cellule) -> 44 + (cellule_to_int cellule)
 | Frapper (cellule) -> 48 + (cellule_to_int cellule)
 | Peindre_R (cellule) -> 52 + (cellule_to_int cellule)
 | Peindre_B (cellule) -> 57 + (cellule_to_int cellule)
 | Attendre -> 62
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

let condition (a:(int*int*int*int) list)= match a with
  |(e1,cond,act,e2)::fin->cond
  |[]->0;;

let rec ajoute_trie ((e1,cond,act,e2):(int*int*int*int)) (l:(int*int*int*int)list)=
  match l with
  |[]->[(e1,cond,act,e2)]
  |(e3,cond2,act2,e4)::fin->if e3<e1
			    then (e3,cond2,act2,e4)::ajoute_trie (e1,cond,act,e2) fin
			    else (e3,cond2,act2,e4)::(e1,cond,act,e2)::fin;;
    
let rec ajoute_transition (l:(int*int*int*int) list list) ((e1,cond,act,e2):(int*int*int*int))=
  match l with
  |[]->[[(e1,cond,act,e2)]]
  |a::fin -> if condition a = cond
	     then let ()= Printf.printf "ajoute trie (%d,%d,%d,%d)\n" e1 cond act e2  in ajoute_trie (e1,cond,act,e2) a :: fin
	     else if condition a < cond
	     then a::ajoute_transition fin (e1,cond,act,e2)
	     else [(e1,cond,act,e2)]::l;;

let rec regroupe_par_cond_aux (l:(int*int*int*int) list) (table:(int*int*int*int) list list)= match l with
  |[]->table
  |a::fin->let r=ajoute_transition table a in regroupe_par_cond_aux fin r;;

let regroupe_par_cond (l:(int*int*int*int) list)=regroupe_par_cond_aux l [];;

let rec parcourir_aux (act:int) (l:(int*int*int*int) list) (nb_etat:int) (etat_actuel:int) (cond:int):((int*int*int*int) list)=
  match l with
  |[]-> if etat_actuel=nb_etat
	then []
	else (etat_actuel,cond,act,etat_actuel)::(parcourir_aux act [] nb_etat (etat_actuel+1) cond)
  |(e1,cond1,act1,e2)::fin->if etat_actuel=e1
			   then (e1,cond1,act1,e2)::parcourir_aux act fin nb_etat (etat_actuel+1) cond
			    else (etat_actuel,cond,act,etat_actuel)::(parcourir_aux act l nb_etat (etat_actuel+1) cond);;

let rec parcourir (act:int) (l:(int*int*int*int)list list) (nb_etat:int)=
  match l with
  |a::fin->(parcourir_aux act a nb_etat 0 (condition a))::parcourir act fin nb_etat
  |[]->[]
    
let completer (act:int) (l:(int*int*int*int) list) (nb_etats:int)=
  let r=regroupe_par_cond l
  in let y=parcourir act r nb_etats
     in List.concat y;;
  
  
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
  Printf.fprintf fichier "\t\t<action nb_l=\"%d\" nb_c=\"%d\">\n" l (c+1);;

let write_tr_entete (fichier:out_channel) (a:(int*int*int*int) list) ((l,c):(int*int))=
  Printf.fprintf fichier "\t\t<transition nb_l=\"%d\" nb_c=\"%d\">\n" l (c+1);;
  
let rec write_act (f:out_channel) (a:(int*int*int*int) list) (l:int) (debut:bool)=
  match a with
  |(e1,cond,act1,e2)::fin-> if debut
			    then
			      let ()=Printf.fprintf f "\t\t\t<l>\n"
			      and ()=Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int cond)
			      and ()=Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int act1)
			      in write_act f fin cond false
			    else if l=cond
			    then
			      let ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int act1) 
			      in write_act f fin cond false
			    else 
			      let ()=Printf.fprintf f "\t\t\t</l>\n" 
			      and ()=Printf.fprintf f "\t\t\t<l>\n"
			      and ()=Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int cond)
			      and ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int act1)
			      in write_act f fin cond false
  |[]-> let ()=Printf.fprintf f "\t\t\t</l>\n" 
	in Printf.fprintf f "\t\t</action>\n" ;;
  
let rec write_tr (f:out_channel) (a:(int*int*int*int) list) (l:int) (debut:bool)=
  match a with
  |(e1,cond,act1,e2)::fin-> if debut
			    then
			      let ()=Printf.fprintf f "\t\t\t<l>\n"
			      and ()=Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int cond)
			      and ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int e2)
			      in write_tr f fin cond false
			    else if l=cond
			    then
			      let ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int e2) 
			      in write_tr f fin cond false
			    else 
			      let ()=Printf.fprintf f "\t\t\t</l>\n" 
			      and ()=Printf.fprintf f "\t\t\t<l>\n"
			      and ()=Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int cond)
			      and ()= Printf.fprintf f "\t\t\t\t<c>%s</c>\n" (string_of_int e2)
			      in write_tr f fin cond false
  |[]-> let ()=Printf.fprintf f "\t\t\t</l>\n" 
	in Printf.fprintf f "\t\t</transition>\n" ;;

let rec write_aut (fichier:out_channel) (list: ((int * int * int * int) list * personnage) list) (act:int) =
  match list with
  |(a,p)::fin->let ()=Printf.fprintf fichier "\t<au personnage=\"%s\">\n\n" (convert p)
	       and (l,c)=compte_lc a
	       in let a= completer act a c
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

let peintre_simple =
  ([(0,CaseBlanche(S),Peindre_B(S),0);
    (0,CaseRouge(S),Peindre_B(S),0);
    (0,CaseBlanche(E),Peindre_B(E),0);
    (0,CaseRouge(E),Peindre_B(E),0);
    (0,CaseBlanche(N),Peindre_B(N),0);
    (0,CaseRouge(N),Peindre_B(N),0);
    (0,CaseBlanche(O),Peindre_B(O),0);
    (0,CaseRouge(O),Peindre_B(O),0);]
  ,Peintre);;
  
let tour_de_map =
  ([(0,CaseBlanche(S),Avancer(S),0);
    (0,CaseBleu(S),Avancer(S),0);
    (0,CaseRouge(S),Avancer(S),0);
    (0,Mur(S),Avancer(E), 1);
    (1,CaseBlanche(E),Avancer(E),1);
    (1,CaseBleu(E),Avancer(E),1);
    (1,CaseRouge(E),Avancer(E),1);
    (1,Mur(E),Avancer(N),2);
    (2,CaseBlanche(N),Avancer(N),2);
    (2,CaseBleu(N),Avancer(N),2);
    (2,CaseRouge(N),Avancer(N),2);
    (2,Mur(N),Avancer(O),3);
    (3,CaseBlanche(O),Avancer(O),3);
    (3,CaseBleu(O),Avancer(O),3);
    (3,CaseRouge(O),Avancer(O),3);
    (3,Mur(O),Avancer(S),0)]
  ,Guerrier);;
(* Guerrier qui tappe les ennemis autours de lui, et traque les ennemis *)
let guerrier_moins_con_B = 
  ([(0,Ennemi(S),Frapper(S),0);
   (0,Ennemi(O),Frapper(O),0);
   (0,Ennemi(N),Frapper(N),0);
   (0,Ennemi(E),Frapper(E),0);
   (0,EnnemiEloigne(S),Avancer(S),1);
   (0,EnnemiEloigne(O),Avancer(O),1);
   (0,EnnemiEloigne(N),Avancer(N),1);
   (0,EnnemiEloigne(E),Avancer(E),1);
   (0,CaseRouge(S),Avancer(S),0);
   (0,CaseRouge(O),Avancer(O),0);
   (0,CaseRouge(N),Avancer(N),0);
   (0,CaseRouge(E),Avancer(E),0);
   (0,CaseBlanche(S),Avancer(S),0);
   (0,CaseBlanche(O),Avancer(O),0);
   (0,CaseBlanche(N),Avancer(N),0);
   (0,CaseBlanche(E),Avancer(E),0);
   (1,EnnemiEloigne(S),Avancer(S),1);
   (1,EnnemiEloigne(O),Avancer(O),1);
   (1,EnnemiEloigne(N),Avancer(N),1);
   (1,EnnemiEloigne(E),Avancer(E),1);
   (1,Ennemi(S),Frapper(S),0);
   (1,Ennemi(O),Frapper(O),0);
   (1,Ennemi(N),Frapper(N),0);
   (1,Ennemi(E),Frapper(E),0);
   (1,CaseBlancheEloignee(S),Avancer(S),0);
   (1,CaseBlancheEloignee(O),Avancer(O),0);
   (1,CaseBlancheEloignee(N),Avancer(N),0);
   (1,CaseBlancheEloignee(E),Avancer(E),0)]
   ,Guerrier);;

(* Peintre qui fuit quand il voit une ennemi eloigne : etat 1 et peint sinon *) 
let peintre_moins_con_B =
  ([(0,EnnemiEloigne(S),Avancer(N),1);
    (0,EnnemiEloigne(O),Avancer(E),1);
    (0,EnnemiEloigne(N),Avancer(S),1);
    (0,EnnemiEloigne(E),Avancer(O),1);
    (0,CaseBlanche(S),Peindre_B(S),0);
    (0,CaseBlanche(O),Peindre_B(O),0);
    (0,CaseBlanche(N),Peindre_B(N),0);
    (0,CaseBlanche(E),Peindre_B(E),0);
    (0,CaseBlancheEloignee(S),Avancer(S),0);
    (0,CaseBlancheEloignee(O),Avancer(O),0);
    (0,CaseBlancheEloignee(N),Avancer(N),0);
    (0,CaseBlancheEloignee(E),Avancer(E),0);
    (1,EnnemiEloigne(S),Avancer(N),1);
    (1,EnnemiEloigne(O),Avancer(E),1);
    (1,EnnemiEloigne(N),Avancer(S),1);
    (1,EnnemiEloigne(E),Avancer(O),1);
    (1,Ennemi(E),Peindre_B(S),0);
    (1,Mur(E),Avancer(O),0);
    (1,Mur(O),Avancer(E),0);
    (1,Mur(N),Avancer(S),0);
    (1,Mur(S),Avancer(N),0)],Peintre);;
   


let guerrier_moins_con_R=
([(0,Ennemi(S),Frapper(S),0);
   (0,Ennemi(O),Frapper(O),0);
   (0,Ennemi(N),Frapper(N),0);
   (0,Ennemi(E),Frapper(E),0);
   (0,EnnemiEloigne(S),Avancer(S),1);
   (0,EnnemiEloigne(O),Avancer(O),1);
   (0,EnnemiEloigne(N),Avancer(N),1);
   (0,EnnemiEloigne(E),Avancer(E),1);
   (0,CaseBleu(S),Avancer(S),0);
   (0,CaseBleu(O),Avancer(O),0);
   (0,CaseBleu(N),Avancer(N),0);
   (0,CaseBleu(E),Avancer(E),0);
   (0,CaseBlanche(S),Avancer(S),0);
   (0,CaseBlanche(O),Avancer(O),0);
   (0,CaseBlanche(N),Avancer(N),0);
   (0,CaseBlanche(E),Avancer(E),0);
   (1,EnnemiEloigne(S),Avancer(S),1);
   (1,EnnemiEloigne(O),Avancer(O),1);
   (1,EnnemiEloigne(N),Avancer(N),1);
   (1,EnnemiEloigne(E),Avancer(E),1);
   (1,Ennemi(S),Frapper(S),0);
   (1,Ennemi(O),Frapper(O),0);
   (1,Ennemi(N),Frapper(N),0);
   (1,Ennemi(E),Frapper(E),0);
   (1,CaseBlancheEloignee(S),Avancer(S),0);
   (1,CaseBlancheEloignee(O),Avancer(O),0);
   (1,CaseBlancheEloignee(N),Avancer(N),0);
   (1,CaseBlancheEloignee(E),Avancer(E),0)]
   ,Guerrier);;

(* Peintre qui fuit quand il voit une ennemi eloigne : etat 1 et peint sinon *) 
let peintre_moins_con_R =
  ([(0,EnnemiEloigne(S),Avancer(N),1);
    (0,EnnemiEloigne(O),Avancer(E),1);
    (0,EnnemiEloigne(N),Avancer(S),1);
    (0,EnnemiEloigne(E),Avancer(O),1);
    (0,CaseBlanche(S),Peindre_R(S),0);
    (0,CaseBlanche(O),Peindre_R(O),0);
    (0,CaseBlanche(N),Peindre_R(N),0);
    (0,CaseBlanche(E),Peindre_R(E),0);
    (0,CaseBlancheEloignee(S),Avancer(S),0);
    (0,CaseBlancheEloignee(O),Avancer(O),0);
    (0,CaseBlancheEloignee(N),Avancer(N),0);
    (0,CaseBlancheEloignee(E),Avancer(E),0);
    (1,EnnemiEloigne(S),Avancer(N),1);
    (1,EnnemiEloigne(O),Avancer(E),1);
    (1,EnnemiEloigne(N),Avancer(S),1);
    (1,EnnemiEloigne(E),Avancer(O),1);
    (1,Ennemi(E),Peindre_R(S),0);
    (1,Mur(E),Avancer(O),0);
    (1,Mur(O),Avancer(E),0);
    (1,Mur(N),Avancer(S),0);
    (1,Mur(S),Avancer(N),0)],Peintre);;
(*
let a = traduction_automate tour_de_map;;
let (b1,b2) = tri_aut a;;

let c = completer 0 b1 4;;*)


(*
let aut2 =
  ([(0,A_peindre,Peindre,1);
    (1,Peinte,Avancer,0)],
   Peintre);;
 *)
let test=[tour_de_map;peintre_simple];;
let test=[guerrier_moins_con_B;peintre_moins_con_B;guerrier_moins_con_R;peintre_moins_con_R];;
(* TEST *)

let fichier = open_out "test1.xml";;
let () = write_xml fichier test 0;;
  close_out fichier;;
 

(* TYPES *)
(*Automates*)

let peintre_simple =
  ([(0,A.CaseBlanche(S),A.Peindre_B(S),0);
    (0,CaseRouge(S),Peindre_B(S),0);
    (0,CaseBlanche(E),Peindre_B(E),0);
    (0,CaseRouge(E),Peindre_B(E),0);
    (0,CaseBlanche(N),Peindre_B(N),0);
    (0,CaseRouge(N),Peindre_B(N),0);
    (0,CaseBlanche(O),Peindre_B(O),0);
    (0,CaseRouge(O),Peindre_B(O),0);]
  ,A.Peintre);;
  
let tour_de_map =
  ([(0,A.CaseBlanche(S),A.Avancer(S),0);
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
  ,A.Guerrier);;
(* Guerrier qui tappe les ennemis autours de lui, et traque les ennemis *)
let guerrier_moins_con_B = 
  ([(0,A.Ennemi(S),A.Frapper(S),0);
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
   ,A.Guerrier);;

(* Peintre qui fuit quand il voit une ennemi eloigne : etat 1 et peint sinon *) 
let peintre_moins_con_B =
  ([(0,A.EnnemiEloigne(S),A.Avancer(N),1);
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
    (1,Mur(S),Avancer(N),0)],A.Peintre);;
   


let guerrier_moins_con_R=
([(0,A.Ennemi(S),A.Frapper(S),0);
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
   ,A.Guerrier);;

(* Peintre qui fuit quand il voit une ennemi eloigne : etat 1 et peint sinon *) 
let peintre_moins_con_R =
  ([(0,A.EnnemiEloigne(S),A.Avancer(N),1);
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
    (1,Mur(S),Avancer(N),0)],A.Peintre);;


(*
let a = traduction_automate tour_de_map;;
let (b1,b2) = tri_aut a;;

let c = completer 0 b1 4;;*)

let test=[tour_de_map;peintre_simple;guerrier_moins_con_R;peintre_moins_con_B];;

(* TEST *)

let fichier = open_out "test_version_makefile.xml";;
let () = A.write_xml fichier test 0;;
let ()=  close_out fichier;;

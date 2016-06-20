(* TYPES *)
(*Automates*)
let peintre_simple =
  ([(0,A.CaseBlanche(S),A.Peindre_B(S),0);
    (0,CaseRouge(S),Peindre_B(S),0);
    (0,CaseBleu(S),Avancer(S),0);
    (0,Mur(S),Avancer(E),0);
    (0,CaseBlanche(E),Peindre_B(E),0);
    (0,CaseRouge(E),Peindre_B(E),0);
    (0,CaseBleu(E),Avancer(E),0);
    (0,Mur(E),Avancer(N),0);
    (0,CaseBlanche(N),Peindre_B(N),0);
    (0,CaseRouge(N),Peindre_B(N),0);
    (0,CaseBleu(N),Avancer(N),0);
    (0,Mur(N),Avancer(O),0);
    (0,CaseBlanche(O),Peindre_B(O),0);
    (0,CaseRouge(O),Peindre_B(O),0);
    (0,CaseBleu(O),Avancer(O),0);
    (0,Mur(O),Avancer(S),0)]
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


(*
let a = traduction_automate tour_de_map;;
let (b1,b2) = tri_aut a;;

let c = completer 0 b1 4;;*)

let test=[tour_de_map;peintre_simple];;

(* TEST *)

let fichier = open_out "test1.xml";;
let () = A.write_xml fichier test 0;;
let ()=  close_out fichier;;

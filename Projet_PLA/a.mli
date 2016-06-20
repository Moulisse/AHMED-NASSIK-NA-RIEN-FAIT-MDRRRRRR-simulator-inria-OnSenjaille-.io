type cellule = C | N | S | E | O
type action =
    Na
  | Avancer of cellule
  | Frapper of cellule
  | Peindre_R of cellule
  | Peindre_B of cellule
  | Attendre
type condition =
    EnnemiEloigne of cellule
  | Ennemi of cellule
  | Allie of cellule
  | CaseBlancheEloignee of cellule
  | CaseRougeEloignee of cellule
  | CaseBleuEloignee of cellule
  | CaseBlanche of cellule
  | CaseRouge of cellule
  | CaseBleu of cellule
  | Mur of cellule
type etat = int
type transition = etat * condition * action * etat
type automate = transition list
type personnage = Guerrier | Peintre
val write_xml : out_channel -> (automate * personnage) list -> int -> unit

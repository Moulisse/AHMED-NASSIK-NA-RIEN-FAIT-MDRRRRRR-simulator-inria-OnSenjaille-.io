package moteur;

public enum TypeAction {
	FRAPPE,
	RATE,		//Un guerrier rate si il frappe dans le vide, ou si il manque son adversaire lors d'une bataille. (33% de chances)
	PEINT,
	MOUVEMENT
}

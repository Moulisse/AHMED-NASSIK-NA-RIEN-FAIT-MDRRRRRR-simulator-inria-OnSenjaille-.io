package graphique;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import moteur.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import java.util.*;

import javafx.animation.*;

public class Grande extends Application {
	
	public int x, y; // coordonn�es joueur 1
	public int x2, y2; // coordonn�es joueur 2
	int sum1 = 0; // score joueur 1
	int sum2 = 0; // score joueur 2
	int sum = 0; // somme totale
	int timer1 = 0;
	int timer2 = 0;
	int timertour = 0;

	public String nom1 = "�quipe 1";
	public String nom2 = "�quipe 2";
	public Color couleur1 = Color.web("#7CFC00");
	public Color couleur2 = Color.web("#1E90FF");
	public int nb_p1 = 2;
	public int nb_p2 = 2;
	public int nb_g1 = 3;
	public int nb_g2 = 3;

	
	int M; // nombre de colonnes
	int N; // nombre de lignes
	
	Cellule tab[][];// Cellule[M + 1][N + 1]; // tableau de cases
	
	int avance1[] = { 0, 0 }; // joueur 1 n'avance pas
	int avance2[] = { 0, 0 }; // joueur 2 n'avance pas

	Rectangle score1 = new Rectangle();
	Rectangle score2 = new Rectangle();
	Text score1t = new Text();
	Text score2t = new Text();
	Text fin = new Text();
	Text temps = new Text();

	int i, j, k;
	long duree = 60;
	long t0 = duree;

	Image image;
	public float buttX = 1070;
	public float buttX2 = 830;

	LinkedList<Node> l_menu = new LinkedList<Node>();
	LinkedList<Node> l_option = new LinkedList<Node>();

	VBox box1 = new VBox(0);
	VBox box2 = new VBox(0);
	HBox hbox1 = new HBox(0);
	HBox hbox2 = new HBox(0);
	Rectangle rekt;
	ImageView balise;
	ImageView guerrier;
	ImageView peintre;
	Pane pane;
	Rectangle mapj1;
	Rectangle mapj2;
	Circle cercle;
	Personnage pers;
	
	@Override
	public void start(Stage primaryStage) {
		
		tab= Main.Main.p.decor();// Cellule[M + 1][N + 1]; // tableau de cases
		
		//System.out.print(tab.length+" "+tab[0].length);
		
		N = Main.Main.p.decor().length; // nombre de lignes
		M = Main.Main.p.decor()[25].length; // nombre de colonnes
		
		
		primaryStage.setTitle("Splatane, un jeu qu'il est bien pour jouer");
		primaryStage.setResizable(false);
		;
		// -----------------  CREATION DE LA FENETRE  -----------------
		Group root = new Group();
		Scene scene = new Scene(root, 1920, 1080, Color.LIGHTGRAY);

		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);

		// ============================ JEU ===========================
		// ------------------------ TITRE ------------------------
		image = new Image("file:images/Splatane.png");
		ImageView titre = new ImageView(image);
		titre.setX(20);
		titre.setY(30);

		// ------------------------ TEMPS ------------------------
		temps.setY(125);
		temps.setText("60");
		temps.setTextAlignment(TextAlignment.RIGHT);
		temps.setFill(Color.rgb(1, 1, 59));
		temps.setFont(Font.font(null, FontWeight.BOLD, 80));
		temps.setX(1840 - com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(temps.getText(),
				temps.getFont()) / 2);

		// ----------------- SCOREBOARD (max:1947) -------------------
		Rectangle scorefont = new Rectangle();
		scorefont.setX(20);
		scorefont.setY(1000);
		scorefont.setWidth(1880);
		scorefont.setHeight(60);
		scorefont.setFill(Color.rgb(188, 188, 206));
		scorefont.setArcHeight(30);
		scorefont.setArcWidth(30);
		Rectangle scorefont2 = new Rectangle();
		scorefont2.setX(30);
		scorefont2.setY(1010);
		scorefont2.setWidth(1860);
		scorefont2.setHeight(40);
		scorefont2.setFill(Color.rgb(195, 195, 215));
		Rectangle scorefont3 = new Rectangle();
		scorefont3.setX(959);
		scorefont3.setY(1010);
		scorefont3.setWidth(3);
		scorefont3.setHeight(40);
		scorefont3.setFill(Color.rgb(188, 188, 206));

		score1.setX(30);
		score1.setY(1010);
		score1.setWidth((float) sum1 / (float) sum * 1860);
		score1.setHeight(40);
		score1.setFill(couleur1);

		score1t.setX(40);
		score1t.setY(1042);
		score1t.setText((float) Math.round((float) sum1 / (float) sum * 1000) / 10 + " %");
		score1t.setFont(Font.font(null, FontWeight.BOLD, 36));
		score1t.toFront();

		score2.setX(1890 - (float) sum2 / (float) sum * 1860);
		score2.setY(1010);
		score2.setWidth((float) sum2 / (float) sum * 1860);
		score2.setHeight(40);
		score2.setFill(couleur2);

		if ((float) Math.round((float) sum2 / (float) sum * 1000) / 10 < 10) {
			score2t.setX(1790);
		} else if ((float) Math.round((float) sum2 / (float) sum * 1000) / 10 < 100) {
			score2t.setX(1769);
		} else {
			score2t.setX(1748);
		}
		score2t.setY(1042);
		score2t.setText((float) Math.round((float) sum2 / (float) sum * 1000) / 10 + " %");
		score2t.setFont(Font.font(null, FontWeight.BOLD, 36));
		score1t.toFront();

		fin.setY(300);
		fin.setTextAlignment(TextAlignment.CENTER);
		fin.setText(nom2 + " remporte la partie");
		fin.setFont(Font.font(null, FontWeight.BOLD, 70));
		fin.setOpacity(0);
		fin.toFront();
		root.getChildren().add(fin);

		Rectangle font1 = new Rectangle();
		font1.setWidth(909);
		font1.setHeight(785);
		font1.setX(35);
		font1.setY(195);
		font1.setFill(couleur1);

		Rectangle font2 = new Rectangle();
		font2.setWidth(909);
		font2.setHeight(785);
		font2.setX(976);
		font2.setY(195);
		font2.setFill(couleur2);

		Rectangle map = new Rectangle();
		map.setWidth(350);
		map.setHeight(199);
		map.setStroke(Color.BLACK);
		map.setX(960 - map.getWidth() / 2);
		map.setY(100 - map.getHeight() / 2);
		map.setFill(Color.DARKGRAY);

		float coef = (float) Math.min(320.0 / (float) M, 180.0 / (float) N);
		Rectangle map2 = new Rectangle();
		map2.setWidth(coef * M);
		map2.setHeight(coef * N);
		map2.setX(960 - map2.getWidth() / 2);
		map2.setY(100 - map2.getHeight() / 2);
		map2.setFill(Color.WHITE);

		AnimationTimer jeu = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if (t0 == duree) {
					t0 = currentNanoTime;
				}
				if (duree - (currentNanoTime - t0) / 1000000000 > 0)
					temps.setText(String.valueOf(duree - (currentNanoTime - t0) / 1000000000));
				else
					temps.setText("");
				temps.setX(1840 - com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader()
						.computeStringWidth(temps.getText(), temps.getFont()) / 2);

				// TEST DE VICTOIRE
				if (sum1 + sum2 >= sum | duree - (currentNanoTime - t0) / 1000000000 <= 0) {
					if (sum1 > sum2) {
						fin.setText(nom1 + " remporte la partie");
					}
					if (sum1 == sum2)
						fin.setText("Egalite");
					fin.setX(960 - com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader()
							.computeStringWidth(fin.getText(), fin.getFont()) / 2);
					fin.setOpacity(1);
					fin.toFront();
					temps.setText("");
					return;
				}
				
				
				
				
				
				if (timertour==0){
					Main.Main.p.tour();
					timertour=15;
				}
				if (timertour > 0)
					timertour--;

				
				// JOUEUR 1
				if (avance1[0] != 0 & timer1 == 0) {
					if (avance1[0] == 1 & tab[y-1][x - 2].valeur() != codes.mur)
						x--;
					if (avance1[0] == 2 & tab[y-1][x].valeur() != codes.mur)
						x++;
					if (avance1[0] == 3 & tab[y - 2][x-1].valeur() != codes.mur)
						y--;
					if (avance1[0] == 4 & tab[y][x-1].valeur() != codes.mur)
						y++;
					timer1 = 4;
				}
				if (timer1 > 0)
					timer1--;

				// JOUEUR 2
				if (avance2[0] != 0 & timer2 == 0) {
					if (avance2[0] == 1 & tab[y2-1][x2 - 2].valeur() != codes.mur)
						x2--;
					if (avance2[0] == 2 & tab[y2-1][x2].valeur() != codes.mur)
						x2++;
					if (avance2[0] == 3 & tab[y2 - 2][x2-1].valeur() != codes.mur)
						y2--;
					if (avance2[0] == 4 & tab[y2][x2-1].valeur() != codes.mur)
						y2++;
					timer2 = 4;
				}
				if (timer2 > 0)
					timer2--;

				root.getChildren().remove(box1);
				box1 = new VBox();
				for (i = Math.max(Math.min(y, N - 12), 13) - 12; i <= Math.max(Math.min(y, N - 12), 13) + 12; i++) {
					hbox1 = new HBox(0);
					for (j = Math.max(Math.min(x, M - 14), 15) - 14; j <= Math.max(Math.min(x, M - 14), 15) + 14; j++) {
						pane = new Pane();
						rekt = new Rectangle();
						pane.getChildren().add(rekt);
						rekt.setWidth(31);
						rekt.setHeight(31);
						rekt.setFill(Color.WHITE);
						if (tab[i-1][j-1].couleur() == codes.bleu)
							rekt.setFill(couleur1);
						if (tab[i-1][j-1].couleur() == codes.rouge)
							rekt.setFill(couleur2);
						
						if (tab[i-1][j-1].valeur() == codes.mur) {
							rekt.setFill(Color.DARKGRAY);
							rekt.setStroke(Color.BLACK);
							rekt.setWidth(30);
							rekt.setHeight(30);
						}
						
						pers = Main.Main.p.occupe(j-1,i-1);
						if (pers!=null) {
							cercle = new Circle();
							cercle.setRadius(14);
							cercle.setCenterX(15);
							cercle.setCenterY(15);
							cercle.setStroke(Color.BLACK);
							cercle.setFill(couleur1);
							//if(pers.equipe().getLEquipeSTP()==2) cercle.setFill(couleur2);
							pane.getChildren().add(cercle);
							if(pers.getClass().equals(Guerrier.class)){
								image = new Image("file:images/epee.png");
								guerrier = new ImageView(image);
								guerrier.setFitWidth(31);
								guerrier.setFitHeight(31);
								pane.getChildren().add(guerrier);
							}else if(pers.getClass().equals(Peintre.class)){
								image = new Image("file:images/peintre.png");
								peintre = new ImageView(image);
								peintre.setFitWidth(31);
								peintre.setFitHeight(31);
								pane.getChildren().add(peintre);
							}
						}
						
						
						if ((i == y & j == x) | (i == y2) & (j == x2)) {
							image = new Image("file:images/balise.png");
							balise = new ImageView(image);
							balise.setFitWidth(31);
							balise.setFitHeight(31);
							pane.getChildren().add(balise);
						} 
						hbox1.getChildren().add(pane);
					}
					box1.getChildren().add(hbox1);
				}

				box1.relocate(40, 200);
				root.getChildren().add(box1);

				root.getChildren().remove(box2);
				box2 = new VBox();
				for (i = Math.max(Math.min(y2, N - 12), 13) - 12; i <= Math.max(Math.min(y2, N - 12), 13) + 12; i++) {
					hbox2 = new HBox(0);
					for (j = Math.max(Math.min(x2, M - 14), 15) - 14; j <= Math.max(Math.min(x2, M - 14), 15) + 14; j++) {
						pane = new Pane();
						rekt = new Rectangle();
						pane.getChildren().add(rekt);
						rekt.setWidth(31);
						rekt.setHeight(31);
						rekt.setFill(Color.WHITE);
						if (tab[i-1][j-1].couleur() == codes.bleu)
							rekt.setFill(couleur1);
						if (tab[i-1][j-1].couleur() == codes.rouge)
							rekt.setFill(couleur2);
						
						if (tab[i-1][j-1].valeur() == codes.mur) {
							rekt.setFill(Color.DARKGRAY);
							rekt.setStroke(Color.BLACK);
							rekt.setWidth(30);
							rekt.setHeight(30);
						}
						
						pers = Main.Main.p.occupe(i-1,j-1);
						if (pers!=null) {
							cercle = new Circle();
							cercle.setRadius(14);
							cercle.setCenterX(15);
							cercle.setCenterY(15);
							cercle.setStroke(Color.BLACK);
							cercle.setFill(couleur1);
							//if(pers.equipe().getLEquipeSTP()==2) cercle.setFill(couleur2);
							pane.getChildren().add(cercle);
							if(pers.getClass().equals(Guerrier.class)){
								image = new Image("file:images/epee.png");
								guerrier = new ImageView(image);
								guerrier.setFitWidth(31);
								guerrier.setFitHeight(31);
								pane.getChildren().add(guerrier);
							}else if(pers.getClass().equals(Peintre.class)){
								image = new Image("file:images/peintre.png");
								peintre = new ImageView(image);
								peintre.setFitWidth(31);
								peintre.setFitHeight(31);
								pane.getChildren().add(peintre);
							}
						}
						
						
						if ((i == y & j == x) | (i == y2) & (j == x2)) {
							image = new Image("file:images/balise.png");
							balise = new ImageView(image);
							balise.setFitWidth(31);
							balise.setFitHeight(31);
							pane.getChildren().add(balise);
						} 
						hbox2.getChildren().add(pane);
					}
					box2.getChildren().add(hbox2);
				}
				box2.relocate(981, 200);
				root.getChildren().add(box2);
				
				map.toFront();
				map2.toFront();
				mapj1.setX(960 + coef * (Math.max(Math.min(x, M - 14), 15) - (M + 1.5) / 2) - mapj1.getWidth() / 2);
				mapj1.setY(100 + coef * (Math.max(Math.min(y, N - 12), 13) - (N + 1.5) / 2) - mapj1.getHeight() / 2);
				mapj1.toFront();
				mapj2.setX(960 + coef * (Math.max(Math.min(x2, M - 14), 15) - (M + 1.5) / 2) - mapj2.getWidth() / 2);
				mapj2.setY(100 + coef * (Math.max(Math.min(y2, N - 12), 13) - (N + 1.5) / 2) - mapj2.getHeight() / 2);
				mapj2.toFront();
				// SCOREBOARD
				score1.setWidth((float) sum1 / (float) sum * 1860);
				score2.setX(1890 - (float) sum2 / (float) sum * 1860);
				score2.setWidth((float) sum2 / (float) sum * 1860);
				score1t.setText((float) Math.round((float) sum1 / (float) sum * 1000) / 10 + " %");
				if ((float) Math.round((float) sum2 / (float) sum * 1000) / 10 < 10) {
					score2t.setX(1790);
				} else if ((float) Math.round((float) sum2 / (float) sum * 1000) / 10 < 100) {
					score2t.setX(1769);
				} else {
					score2t.setX(1748);
				}
				score2t.setText((float) Math.round((float) sum2 / (float) sum * 1000) / 10 + " %");

			}
		};

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode().toString() == "Q" & avance1[0] == 1) {
					avance1[0] = avance1[1];
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "D" & avance1[0] == 2) {
					avance1[0] = avance1[1];
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "Z" & avance1[0] == 3) {
					avance1[0] = avance1[1];
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "S" & avance1[0] == 4) {
					avance1[0] = avance1[1];
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "Q" & avance1[1] == 1) {
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "D" & avance1[1] == 2) {
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "Z" & avance1[1] == 3) {
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "S" & avance1[1] == 4) {
					avance1[1] = 0;
				}
				if (ke.getCode().toString() == "LEFT" & avance2[0] == 1) {
					avance2[0] = avance2[1];
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "RIGHT" & avance2[0] == 2) {
					avance2[0] = avance2[1];
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "UP" & avance2[0] == 3) {
					avance2[0] = avance2[1];
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "DOWN" & avance2[0] == 4) {
					avance2[0] = avance2[1];
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "LEFT" & avance2[1] == 1) {
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "RIGHT" & avance2[1] == 2) {
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "UP" & avance2[1] == 3) {
					avance2[1] = 0;
				}
				if (ke.getCode().toString() == "DOWN" & avance2[1] == 4) {
					avance2[1] = 0;
				}
			}
		});

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode().toString() == "Q" & avance1[0] != 1) {
					avance1[1] = avance1[0];
					avance1[0] = 1;
				}
				if (ke.getCode().toString() == "D" & avance1[0] != 2) {
					avance1[1] = avance1[0];
					avance1[0] = 2;
				}
				if (ke.getCode().toString() == "Z" & avance1[0] != 3) {
					avance1[1] = avance1[0];
					avance1[0] = 3;
				}
				if (ke.getCode().toString() == "S" & avance1[0] != 4) {
					avance1[1] = avance1[0];
					avance1[0] = 4;
				}
				if (ke.getCode().toString() == "LEFT" & avance2[0] != 1) {
					avance2[1] = avance2[0];
					avance2[0] = 1;
				}
				if (ke.getCode().toString() == "RIGHT" & avance2[0] != 2) {
					avance2[1] = avance2[0];
					avance2[0] = 2;
				}
				if (ke.getCode().toString() == "UP" & avance2[0] != 3) {
					avance2[1] = avance2[0];
					avance2[0] = 3;
				}
				if (ke.getCode().toString() == "DOWN" & avance2[0] != 4) {
					avance2[1] = avance2[0];
					avance2[0] = 4;
				}

				if (ke.getCode().toString() == "ESCAPE") {
					System.exit(0);
				}
			}
		});
		primaryStage.show();

		// =========================== OPTIONS ===========================
		image = new Image("file:images/options.png");
		ImageView opt = new ImageView(image);
		opt.setX(100);
		opt.setY(40);
		l_option.add(opt);
		opt.setVisible(false);
		root.getChildren().add(opt);

		Rectangle opt_trait = new Rectangle();
		opt_trait.setX(100);
		opt_trait.setY(160);
		opt_trait.setWidth(1720);
		opt_trait.setHeight(5);
		opt_trait.setFill(Color.rgb(1, 1, 49));
		l_option.add(opt_trait);
		opt_trait.setVisible(false);
		root.getChildren().add(opt_trait);

		Rectangle opt_trait2 = new Rectangle();
		opt_trait2.setX(959);
		opt_trait2.setY(200);
		opt_trait2.setWidth(4);
		opt_trait2.setHeight(720);
		opt_trait2.setFill(Color.rgb(1, 1, 49));
		l_option.add(opt_trait2);
		opt_trait2.setVisible(false);
		root.getChildren().add(opt_trait2);

		Rectangle o_equipe1_font = new Rectangle();
		o_equipe1_font.setX(100);
		o_equipe1_font.setY(230);
		o_equipe1_font.setWidth(800);
		o_equipe1_font.setHeight(270);
		o_equipe1_font.setFill(Color.rgb(205, 205, 205));
		l_option.add(o_equipe1_font);
		o_equipe1_font.setVisible(false);
		root.getChildren().add(o_equipe1_font);
		image = new Image("file:images/o_equipe1.png");
		ImageView o_equipe1 = new ImageView(image);
		o_equipe1.setX(123);
		o_equipe1.setY(344);
		l_option.add(o_equipe1);
		o_equipe1.setVisible(false);
		root.getChildren().add(o_equipe1);
		Rectangle o_equipe1_trait = new Rectangle();
		o_equipe1_trait.setX(300);
		o_equipe1_trait.setY(230);
		o_equipe1_trait.setWidth(3);
		o_equipe1_trait.setHeight(270);
		o_equipe1_trait.setFill(Color.rgb(1, 1, 49));
		l_option.add(o_equipe1_trait);
		o_equipe1_trait.setVisible(false);
		root.getChildren().add(o_equipe1_trait);

		Rectangle o_equipe2_font = new Rectangle();
		o_equipe2_font.setX(100);
		o_equipe2_font.setY(615);
		o_equipe2_font.setWidth(800);
		o_equipe2_font.setHeight(270);
		o_equipe2_font.setFill(Color.rgb(205, 205, 205));
		l_option.add(o_equipe2_font);
		o_equipe2_font.setVisible(false);
		root.getChildren().add(o_equipe2_font);
		image = new Image("file:images/o_equipe2.png");
		ImageView o_equipe2 = new ImageView(image);
		o_equipe2.setX(120);
		o_equipe2.setY(729);
		l_option.add(o_equipe2);
		o_equipe2.setVisible(false);
		root.getChildren().add(o_equipe2);
		Rectangle o_equipe2_trait = new Rectangle();
		o_equipe2_trait.setX(300);
		o_equipe2_trait.setY(615);
		o_equipe2_trait.setWidth(3);
		o_equipe2_trait.setHeight(270);
		o_equipe2_trait.setFill(Color.rgb(1, 1, 49));
		l_option.add(o_equipe2_trait);
		o_equipe2_trait.setVisible(false);
		root.getChildren().add(o_equipe2_trait);

		image = new Image("file:images/o_nom.png");
		ImageView o_nom1 = new ImageView(image);
		o_nom1.setX(330);
		o_nom1.setY(250);
		l_option.add(o_nom1);
		o_nom1.setVisible(false);
		root.getChildren().add(o_nom1);
		ImageView o_nom2 = new ImageView(image);
		o_nom2.setX(330);
		o_nom2.setY(635);
		l_option.add(o_nom2);
		o_nom2.setVisible(false);
		root.getChildren().add(o_nom2);

		TextField o_nom1_textfield = new TextField();
		o_nom1_textfield.setText(nom1);
		o_nom1_textfield.setAlignment(Pos.CENTER);
		o_nom1_textfield.setStyle(
				"-fx-background-color:#D5D5D5;     -fx-font-size: 30px; -fx-font-weight: bold;    -fx-padding: -5");
		o_nom1_textfield.setFocusTraversable(false);
		HBox o_nom1_box = new HBox();
		o_nom1_box.getChildren().add(o_nom1_textfield);
		o_nom1_box.relocate(430, 240);
		l_option.add(o_nom1_box);
		o_nom1_box.setVisible(false);
		root.getChildren().add(o_nom1_box);
		TextField o_nom2_textfield = new TextField();
		o_nom2_textfield.setText(nom2);
		o_nom2_textfield.setAlignment(Pos.CENTER);
		o_nom2_textfield.setStyle(
				"-fx-background-color:#D5D5D5;     -fx-font-size: 30px; -fx-font-weight: bold;    -fx-padding: -5");
		o_nom2_textfield.setFocusTraversable(false);
		HBox o_nom2_box = new HBox();
		o_nom2_box.getChildren().add(o_nom2_textfield);
		o_nom2_box.relocate(430, 625);
		l_option.add(o_nom2_box);
		o_nom2_box.setVisible(false);
		root.getChildren().add(o_nom2_box);

		image = new Image("file:images/o_couleur.png");
		ImageView o_couleur1 = new ImageView(image);
		o_couleur1.setX(330);
		o_couleur1.setY(316);
		l_option.add(o_couleur1);
		o_couleur1.setVisible(false);
		root.getChildren().add(o_couleur1);
		ImageView o_couleur2 = new ImageView(image);
		o_couleur2.setX(330);
		o_couleur2.setY(701);
		l_option.add(o_couleur2);
		o_couleur2.setVisible(false);
		root.getChildren().add(o_couleur2);

		ColorPicker o_colorPicker1 = new ColorPicker();
		o_colorPicker1.setValue(couleur1);
		HBox o_couleur1_box = new HBox();
		o_couleur1_box.getChildren().add(o_colorPicker1);
		o_couleur1_box.relocate(470, 314);
		l_option.add(o_couleur1_box);
		o_couleur1_box.setVisible(false);
		root.getChildren().add(o_couleur1_box);
		ColorPicker o_colorPicker2 = new ColorPicker();
		o_colorPicker2.setValue(couleur2);
		HBox o_couleur2_box = new HBox();
		o_couleur2_box.getChildren().add(o_colorPicker2);
		o_couleur2_box.relocate(470, 699);
		l_option.add(o_couleur2_box);
		o_couleur2_box.setVisible(false);
		root.getChildren().add(o_couleur2_box);

		image = new Image("file:images/o_peintres.png");
		ImageView o_nbp1 = new ImageView(image);
		o_nbp1.setX(330);
		o_nbp1.setY(382);
		l_option.add(o_nbp1);
		o_nbp1.setVisible(false);
		root.getChildren().add(o_nbp1);
		ImageView o_nbp2 = new ImageView(image);
		o_nbp2.setX(330);
		o_nbp2.setY(767);
		l_option.add(o_nbp2);
		o_nbp2.setVisible(false);
		root.getChildren().add(o_nbp2);

		TextField o_nbp1_textfield = new TextField();
		o_nbp1_textfield.setText(Integer.toString(nb_p1));
		o_nbp1_textfield.setAlignment(Pos.CENTER);
		o_nbp1_textfield.setPrefColumnCount(2);
		o_nbp1_textfield.setStyle("-fx-background-color:#D5D5D5;     -fx-font-size: 28px;     -fx-padding: 0");
		o_nbp1_textfield.setFocusTraversable(false);
		HBox o_nbp1_box = new HBox();
		o_nbp1_box.getChildren().add(o_nbp1_textfield);
		o_nbp1_box.relocate(655, 373);
		l_option.add(o_nbp1_box);
		o_nbp1_box.setVisible(false);
		root.getChildren().add(o_nbp1_box);
		TextField o_nbp2_textfield = new TextField();
		o_nbp2_textfield.setText(Integer.toString(nb_p2));
		o_nbp2_textfield.setAlignment(Pos.CENTER);
		o_nbp2_textfield.setPrefColumnCount(2);
		o_nbp2_textfield.setStyle("-fx-background-color:#D5D5D5;     -fx-font-size: 28px;     -fx-padding: 0");
		o_nbp2_textfield.setFocusTraversable(false);
		HBox o_nbp2_box = new HBox();
		o_nbp2_box.getChildren().add(o_nbp2_textfield);
		o_nbp2_box.relocate(655, 755);
		l_option.add(o_nbp2_box);
		o_nbp2_box.setVisible(false);
		root.getChildren().add(o_nbp2_box);

		image = new Image("file:images/o_guerriers.png");
		ImageView o_nbg1 = new ImageView(image);
		o_nbg1.setX(330);
		o_nbg1.setY(448);
		l_option.add(o_nbg1);
		o_nbg1.setVisible(false);
		root.getChildren().add(o_nbg1);
		ImageView o_nbg2 = new ImageView(image);
		o_nbg2.setX(330);
		o_nbg2.setY(833);
		l_option.add(o_nbg2);
		o_nbg2.setVisible(false);
		root.getChildren().add(o_nbg2);

		TextField o_nbg1_textfield = new TextField();
		o_nbg1_textfield.setText(Integer.toString(nb_g1));
		o_nbg1_textfield.setAlignment(Pos.CENTER);
		o_nbg1_textfield.setPrefColumnCount(2);
		o_nbg1_textfield.setStyle("-fx-background-color:#D5D5D5;     -fx-font-size: 28px;     -fx-padding: 0");
		o_nbg1_textfield.setFocusTraversable(false);
		HBox o_nbg1_box = new HBox();
		o_nbg1_box.getChildren().add(o_nbg1_textfield);
		o_nbg1_box.relocate(670, 440);
		l_option.add(o_nbg1_box);
		o_nbg1_box.setVisible(false);
		root.getChildren().add(o_nbg1_box);
		TextField o_nbg2_textfield = new TextField();
		o_nbg2_textfield.setText(Integer.toString(nb_g2));
		o_nbg2_textfield.setAlignment(Pos.CENTER);
		o_nbg2_textfield.setPrefColumnCount(2);
		o_nbg2_textfield.setStyle("-fx-background-color:#D5D5D5;     -fx-font-size: 28px;     -fx-padding: 0");
		o_nbg2_textfield.setFocusTraversable(false);
		HBox o_nbg2_box = new HBox();
		o_nbg2_box.getChildren().add(o_nbg2_textfield);
		o_nbg2_box.relocate(670, 825);
		l_option.add(o_nbg2_box);
		o_nbg2_box.setVisible(false);
		root.getChildren().add(o_nbg2_box);

		// ============================ MENU ===========================
		image = new Image("file:images/peinture_tache.png");
		ImageView tache = new ImageView(image);
		tache.setX(400);
		tache.setY(100);
		tache.setFitHeight(400);
		tache.setFitWidth(400);
		root.getChildren().add(tache);
		l_menu.add(tache);

		image = new Image("file:images/Splatane_grand.png");
		ImageView titre_menu = new ImageView(image);
		titre_menu.setX(500);
		titre_menu.setY(200);
		root.getChildren().add(titre_menu);
		l_menu.add(titre_menu);

		image = new Image("file:images/platane.png");
		ImageView platane = new ImageView(image);
		platane.setX(1150);
		platane.setY(250);
		platane.setFitHeight(400);
		platane.setFitWidth(400);
		root.getChildren().add(platane);
		l_menu.add(platane);

		Button o_exit = new Button("retour");
		o_exit.setOnAction(actionEvent -> {
			// EFFACE L'ECRAN
			for (int i = 0; i < l_option.size(); i++) {
				if (l_option.get(i).getClass().getName() == "javafx.scene.image.ImageView")
					((ImageView) l_option.get(i)).setVisible(false);
				if (l_option.get(i).getClass().getName() == "javafx.scene.control.ToggleButton") {
					((ToggleButton) l_option.get(i)).setVisible(false);
					((ToggleButton) l_option.get(i)).setDisable(true);
				}
				if (l_option.get(i).getClass().getName() == "javafx.scene.control.Button") {
					((Button) l_option.get(i)).setVisible(false);
					((Button) l_option.get(i)).setDisable(true);
				}
				if (l_option.get(i).getClass().getName() == "javafx.scene.layout.HBox")
					((HBox) l_option.get(i)).setVisible(false);
				if (l_option.get(i).getClass().getName() == "javafx.scene.shape.Rectangle")
					((Rectangle) l_option.get(i)).setVisible(false);
			}
			// REAFFICHE LE MENU
			for (int i = 0; i < l_menu.size(); i++) {
				if (l_menu.get(i).getClass().getName() == "javafx.scene.image.ImageView")
					((ImageView) l_menu.get(i)).setVisible(true);
				if (l_menu.get(i).getClass().getName() == "javafx.scene.control.ToggleButton") {
					((ToggleButton) l_menu.get(i)).setVisible(true);
					((ToggleButton) l_menu.get(i)).setDisable(false);
				}
				if (l_menu.get(i).getClass().getName() == "javafx.scene.control.Button") {
					((Button) l_menu.get(i)).setVisible(true);
					((Button) l_menu.get(i)).setDisable(false);
				}
				if (l_menu.get(i).getClass().getName() == "javafx.scene.layout.HBox")
					((HBox) l_menu.get(i)).setVisible(true);
				if (l_menu.get(i).getClass().getName() == "javafx.scene.shape.Rectangle")
					((Rectangle) l_menu.get(i)).setVisible(true);
			}
			score2.setFill(couleur2);
			score1.setFill(couleur1);
		});
		o_exit.setStyle(
				"-fx-background-color:#eeeeee;    -fx-background-radius: 15; -fx-padding: -3 62 -3 62; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		o_exit.relocate(1580, 58);
		o_exit.setOnMouseEntered(actionEvent -> o_exit.setStyle(
				"-fx-background-color:#f5f5f5;    -fx-background-radius: 15; -fx-padding: -3 62 -3 62; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		o_exit.setOnMouseExited(actionEvent -> o_exit.setStyle(
				"-fx-background-color:#eeeeee;    -fx-background-radius: 15; -fx-padding: -3 62 -3 62; -fx-font-size: 35px; -fx-font-weight:	 bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		o_exit.setVisible(false);
		root.getChildren().add(o_exit);
		l_option.add(o_exit);

		Button quitter = new Button("QUITTER");
		quitter.setOnAction(actionEvent -> System.exit(0));
		quitter.setStyle(
				"-fx-background-color:#eeeeee;    -fx-background-radius: 15; -fx-padding: -3 62 -3 62; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		quitter.relocate(825, 630);
		quitter.setOnMouseEntered(actionEvent -> quitter.setStyle(
				"-fx-background-color:#f5f5f5;    -fx-background-radius: 15; -fx-padding: -3 62 -3 62; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		quitter.setOnMouseExited(actionEvent -> quitter.setStyle(
				"-fx-background-color:#eeeeee;    -fx-background-radius: 15; -fx-padding: -3 62 -3 62; -fx-font-size: 35px; -fx-font-weight:	 bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		root.getChildren().add(quitter);
		l_menu.add(quitter);

		AnimationTimer options_anim = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if (o_nom1_textfield.getText() != "")
					nom1 = o_nom1_textfield.getText();
				if (o_nom2_textfield.getText() != "")
					nom2 = o_nom2_textfield.getText();
				couleur1 = o_colorPicker1.getValue();
				couleur2 = o_colorPicker2.getValue();
				if (o_nbp1_textfield.getAnchor() != 0)
					nb_p1 = Integer.parseInt(o_nbp1_textfield.getText());
				if (o_nbp2_textfield.getAnchor() != 0)
					nb_p2 = Integer.parseInt(o_nbp2_textfield.getText());
				if (o_nbg1_textfield.getAnchor() != 0)
					nb_g1 = Integer.parseInt(o_nbg1_textfield.getText());
				if (o_nbg2_textfield.getAnchor() != 0)
					nb_g2 = Integer.parseInt(o_nbg2_textfield.getText());
			}
		};

		Button options = new Button("OPTIONS");
		options.setOnAction(actionEvent -> {
			// EFFACE L'ECRAN
			for (int i = 0; i < l_menu.size(); i++) {
				if (l_menu.get(i).getClass().getName() == "javafx.scene.image.ImageView")
					((ImageView) l_menu.get(i)).setVisible(false);
				if (l_menu.get(i).getClass().getName() == "javafx.scene.control.ToggleButton") {
					((ToggleButton) l_menu.get(i)).setVisible(false);
					((ToggleButton) l_menu.get(i)).setDisable(true);
				}
				if (l_menu.get(i).getClass().getName() == "javafx.scene.control.Button") {
					((Button) l_menu.get(i)).setVisible(false);
					((Button) l_menu.get(i)).setDisable(true);
				}
			}
			// AFFICHE LES OPTIONS
			for (int i = 0; i < l_option.size(); i++) {
				if (l_option.get(i).getClass().getName() == "javafx.scene.image.ImageView")
					((ImageView) l_option.get(i)).setVisible(true);
				if (l_option.get(i).getClass().getName() == "javafx.scene.control.ToggleButton") {
					((ToggleButton) l_option.get(i)).setVisible(true);
					((ToggleButton) l_option.get(i)).setDisable(false);
				}
				if (l_option.get(i).getClass().getName() == "javafx.scene.control.Button") {
					((Button) l_option.get(i)).setVisible(true);
					((Button) l_option.get(i)).setDisable(false);
				}
				if (l_option.get(i).getClass().getName() == "javafx.scene.layout.HBox")
					((HBox) l_option.get(i)).setVisible(true);
				if (l_option.get(i).getClass().getName() == "javafx.scene.shape.Rectangle")
					((Rectangle) l_option.get(i)).setVisible(true);
			}

			options_anim.start();
			;
		});
		options.setStyle(
				"-fx-background-color:#eeeeee;    -fx-background-radius: 15; -fx-padding: -3 58 -3 58; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		options.relocate(825, 550);
		options.setOnMouseEntered(actionEvent -> options.setStyle(
				"-fx-background-color:#f5f5f5;    -fx-background-radius: 15; -fx-padding: -3 58 -3 58; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		options.setOnMouseExited(actionEvent -> options.setStyle(
				"-fx-background-color:#eeeeee;    -fx-background-radius: 15; -fx-padding: -3 58 -3 58; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		root.getChildren().add(options);
		l_menu.add(options);

		Button jouer = new Button("JOUER");
		jouer.setOnAction(actionEvent -> {
			// INITIALISATION
			root.getChildren().add(titre);
			root.getChildren().add(temps);
			root.getChildren().add(scorefont);
			root.getChildren().add(scorefont2);
			root.getChildren().add(scorefont3);
			root.getChildren().add(score2);
			root.getChildren().add(score2t);
			root.getChildren().add(score1);
			root.getChildren().add(score1t);
			root.getChildren().add(font1);
			root.getChildren().add(font2);
			root.getChildren().add(map);
			root.getChildren().add(map2);

			// ---------------- INITIALISATION DU TABLEAU ----------------
			
			for (int i = 1; i < M; i++) {
				for (int j = 1; j < N; j++) {
					if(tab[j][i].valeur()!=codes.mur){
						sum++;
					}
				}
			}
			
			x = 8;
			y = (N + 1) / 2;
			x2 = M-7;
			y2 = (N + 1) / 2;

			mapj1 = new Rectangle();
			mapj1.setWidth(coef * 29);
			mapj1.setHeight(coef * 25);
			mapj1.setStroke(couleur1);
			mapj1.setFill(null);
			root.getChildren().add(mapj1);
			mapj2 = new Rectangle();
			mapj2.setWidth(coef * 29);
			mapj2.setHeight(coef * 25);
			mapj2.setStroke(couleur2);
			mapj2.setFill(null);
			root.getChildren().add(mapj2);

			tab[y-1][x-1].setValeur(0);
			tab[y2-1][x2-1].setValeur(0);
			// EFFACE L'ECRAN
			for (int i = 0; i < l_menu.size(); i++) {
				if (l_menu.get(i).getClass().getName() == "javafx.scene.image.ImageView")
					((ImageView) l_menu.get(i)).setVisible(false);
				if (l_menu.get(i).getClass().getName() == "javafx.scene.control.ToggleButton") {
					((ToggleButton) l_menu.get(i)).setVisible(false);
					((ToggleButton) l_menu.get(i)).setDisable(true);
				}
				if (l_menu.get(i).getClass().getName() == "javafx.scene.control.Button") {
					((Button) l_menu.get(i)).setVisible(false);
					((Button) l_menu.get(i)).setDisable(true);
				}
			}
			jeu.start();
		});
		jouer.setStyle(
				"-fx-background-color:#00ff55;    -fx-background-radius: 15; -fx-padding: -3 80 -3 80; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );");
		jouer.relocate(825, 470);
		jouer.setOnMouseEntered(actionEvent -> jouer.setStyle(
				"-fx-background-color:#33ff77;    -fx-background-radius: 15; -fx-padding: -3 80 -3 80; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		jouer.setOnMouseExited(actionEvent -> jouer.setStyle(
				"-fx-background-color:#00ff55;    -fx-background-radius: 15; -fx-padding: -3 80 -3 80; -fx-font-size: 35px; -fx-font-weight: bold;    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );"));
		root.getChildren().add(jouer);
		l_menu.add(jouer);

	}

}
package com.example.sportxnews.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LiveScoresController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private VBox liveScoresContainer;

    private ObservableList<String> categories = FXCollections.observableArrayList();
    private Map<String, String[]> staticScores = new HashMap<>();
    @FXML
    private VBox scoreCard;

    @FXML
    private void handleMouseEntered() {
        scoreCard.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f8f8f8);"
                + "-fx-padding: 15px; -fx-spacing: 10px;"
                + "-fx-border-radius: 10px; -fx-border-color: #ddd; -fx-border-width: 1px;"
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 16, 0.3, 6, 6);"
                + "-fx-translate-y: -2px;");
    }

    @FXML
    private void handleMouseExited() {
        scoreCard.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f8f8f8);"
                + "-fx-padding: 15px; -fx-spacing: 10px;"
                + "-fx-border-radius: 10px; -fx-border-color: #ddd; -fx-border-width: 1px;"
                + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 12, 0.2, 4, 4),"
                + "innershadow(gaussian, rgba(0, 0, 0, 0.15), 6, 0.3, -2, -2);");
    }

    @FXML
    private void initialize() {
        // Initialiser les catégories et les scores statiques
        initializeStaticScores();

        // Configurer la liste des catégories avec filtrage
        setupCategoryListView();

        // Afficher les scores de la première catégorie par défaut
        if (!categories.isEmpty()) {
            categoryListView.getSelectionModel().selectFirst();
            displayScoresForCategory(categories.get(0));
        }


    }

    private void initializeStaticScores() {
        staticScores.put("⚽ Football", new String[]{
                "Match 1: Real Madrid 2 - 1 FC Barcelona",               // Célèbre
                "Match 2: Union Saint-Gilloise 3 - 2 KV Mechelen",      // Moyen
                "Match 3: River Town 1 - 0 Hill City",                  // Inconnu
                "Match 4: Manchester United 0 - 0 Liverpool",           // Célèbre
                "Match 5: Fortuna Sittard 2 - 1 Excelsior",            // Moyen
                "Match 6: Sunny Plains 3 - 1 Oak Grove",               // Inconnu
                "Match 7: Paris Saint-Germain 4 - 2 Bayern Munich",     // Célèbre
                "Match 8: Vitoria Guimarães 1 - 1 Boavista",           // Moyen
                "Match 9: Blue Coast 2 - 3 Red Hills",                 // Inconnu
                "Match 10: Juventus 0 - 2 AC Milan",                   // Célèbre
                "Match 11: Hammarby IF 3 - 0 IFK Norrköping",          // Moyen
                "Match 12: Pinewood 1 - 1 Stonefield",                // Inconnu
                "Match 13: Chelsea 2 - 2 Arsenal",                    // Célèbre
                "Match 14: St. Mirren 1 - 0 Hibernian",               // Moyen
                "Match 15: Lakeview 3 - 2 West Park",                 // Inconnu
                "Match 16: Manchester City 4 - 1 Tottenham",           // Célèbre
                "Match 17: Legia Warsaw 2 - 1 Wisła Kraków",          // Moyen
                "Match 18: Forest Edge 0 - 3 Maple Town",             // Inconnu
                "Match 19: Inter Milan 1 - 2 Napoli",                 // Célèbre
                "Match 20: Rapid Vienna 2 - 2 Sturm Graz",            // Moyen
                "Match 21: North Valley 1 - 0 East Bridge"            // Inconnu
        });
        staticScores.put("🏀 Basketball", new String[]{
                "Match 1: Lakers 89 - 78 Warriors",                   // Célèbre
                "Match 2: Basket Zaragoza 95 - 90 Bilbao Basket",     // Moyen
                "Match 3: Skyhawks 102 - 98 Thunderbolts",           // Inconnu
                "Match 4: Bulls 91 - 89 Celtics",                    // Célèbre
                "Match 5: BC Oostende 88 - 85 Limburg United",       // Moyen
                "Match 6: Ravens 96 - 94 Falcons",                  // Inconnu
                "Match 7: Nets 100 - 99 Bucks",                     // Célèbre
                "Match 8: Rasta Vechta 92 - 87 Niners Chemnitz",    // Moyen
                "Match 9: Wolves 85 - 82 Bears",                   // Inconnu
                "Match 10: Clippers 93 - 90 Mavericks",            // Célèbre
                "Match 11: Hapoel Haifa 97 - 95 Ironi Ness Ziona", // Moyen
                "Match 12: Eagles 88 - 86 Panthers",              // Inconnu
                "Match 13: Heat 94 - 92 Knicks",                  // Célèbre
                "Match 14: Sopron KC 101 - 99 Falco Szombathely", // Moyen
                "Match 15: Tigers 90 - 89 Sharks",               // Inconnu
                "Match 16: Suns 87 - 85 Nuggets",                // Célèbre
                "Match 17: Tsmoki Minsk 93 - 91 Borisfen",       // Moyen
                "Match 18: Dragons 98 - 96 Lions",              // Inconnu
                "Match 19: Raptors 99 - 97 76ers",              // Célèbre
                "Match 20: Levski Sofia 86 - 84 Rilski Sportist",// Moyen
                "Match 21: Hawks 103 - 100 Owls"               // Inconnu
        });
        staticScores.put("🎾 Tennis", new String[]{
                "Match 1: Novak Djokovic 6-4 3-6 7-5 Rafael Nadal",      // Célèbre
                "Match 2: Thiago Monteiro 6-2 6-3 Tallon Griekspoor",   // Moyen
                "Match 3: Alex Porter 7-6 6-4 Jamie Carter",           // Inconnu
                "Match 4: Roger Federer 6-1 6-2 Andy Murray",          // Célèbre
                "Match 5: Bernabe Zapata Miralles 6-3 6-3 Jaume Munar",// Moyen
                "Match 6: Liam Brooks 6-0 6-1 Ethan Wells",           // Inconnu
                "Match 7: Daniil Medvedev 7-6 6-4 Stefanos Tsitsipas", // Célèbre
                "Match 8: Federico Coria 6-4 3-6 7-5 Pedro Martinez", // Moyen
                "Match 9: Ryan Hayes 6-2 6-3 Sam Turner",            // Inconnu
                "Match 10: Serena Williams 6-1 6-2 Naomi Osaka",     // Célèbre
                "Match 11: Marie Bouzkova 6-3 6-3 Anna Bondar",      // Moyen
                "Match 12: Emma Stone 6-0 6-1 Clara Evans",         // Inconnu
                "Match 13: Ashleigh Barty 7-6 6-4 Simona Halep",    // Célèbre
                "Match 14: Panna Udvardy 6-2 6-3 Kaja Juvan",       // Moyen
                "Match 15: Sophie Lane 6-4 3-6 7-5 Mia Harper",    // Inconnu
                "Match 16: Alexander Zverev 6-1 6-2 Dominic Thiem", // Célèbre
                "Match 17: Hugo Gaston 6-3 6-3 Facundo Bagnis",    // Moyen
                "Match 18: Noah King 7-6 6-4 Lucas Gray",          // Inconnu
                "Match 19: Bianca Andreescu 6-4 6-4 Karolina Pliskova", // Célèbre
                "Match 20: Viktoriya Tomova 6-2 6-3 Lesia Tsurenko",   // Moyen
                "Match 21: Zoe Parker 6-1 6-2 Ella Ford"             // Inconnu
        });
        staticScores.put("🏐 Volleyball", new String[]{
                "Match 1: Brazil 3 - 2 USA",                          // Célèbre
                "Match 2: OK Vojvodina 3 - 1 Partizan Belgrade",     // Moyen
                "Match 3: Team Alpha 3 - 0 Team Beta",              // Inconnu
                "Match 4: Russia 3 - 2 Italy",                     // Célèbre
                "Match 5: ACH Volley Ljubljana 3 - 1 Calcit Kamnik",// Moyen
                "Match 6: Coastal Spikes 3 - 0 Mountain Nets",     // Inconnu
                "Match 7: Poland 3 - 1 France",                   // Célèbre
                "Match 8: Dinamo Bucharest 3 - 2 CSM Arcada Galați",// Moyen
                "Match 9: Valley Strikers 3 - 1 City Aces",        // Inconnu
                "Match 10: Serbia 3 - 2 Argentina",               // Célèbre
                "Match 11: Volley Lube 3 - 0 Top Volley Cisterna",// Moyen
                "Match 12: River Smashers 3 - 1 Plains Defenders",// Inconnu
                "Match 13: Japan 3 - 1 Canada",                  // Célèbre
                "Match 14: Fenerbahçe Istanbul 3 - 2 Galatasaray",// Moyen
                "Match 15: Eastern Blocks 3 - 0 Northern Stars",  // Inconnu
                "Match 16: Iran 3 - 2 Egypt",                    // Célèbre
                "Match 17: Ziraat Bankası 3 - 1 Arkas Spor",     // Moyen
                "Match 18: Southern Flames 3 - 2 Western Winds", // Inconnu
                "Match 19: China 3 - 1 South Korea",            // Célèbre
                "Match 20: Zenit St. Petersburg 3 - 2 Lokomotiv Novosibirsk", // Moyen
                "Match 21: Urban Diggers 3 - 0 Rural Spikers"   // Inconnu
        });
        staticScores.put("⚾ Baseball", new String[]{
                "Match 1: Yankees 5 - 3 Red Sox",                    // Célèbre
                "Match 2: Hiroshima Toyo Carp 4 - 2 Chunichi Dragons", // Moyen
                "Match 3: Green Bats 6 - 5 Blue Caps",              // Inconnu
                "Match 4: Dodgers 7 - 1 Giants",                   // Célèbre
                "Match 5: Orix Buffaloes 3 - 2 Rakuten Eagles",    // Moyen
                "Match 6: Red Gloves 8 - 6 Yellow Socks",         // Inconnu
                "Match 7: Cubs 4 - 4 Cardinals",                 // Célèbre
                "Match 8: Yokohama DeNA BayStars 5 - 3 Hanshin Tigers", // Moyen
                "Match 9: White Pitchers 6 - 5 Black Bases",     // Inconnu
                "Match 10: Astros 3 - 2 White Sox",             // Célèbre
                "Match 11: Fukuoka SoftBank Hawks 7 - 1 Saitama Seibu Lions", // Moyen
                "Match 12: Silver Sliders 4 - 2 Golden Catchers",// Inconnu
                "Match 13: Braves 8 - 6 Mets",                 // Célèbre
                "Match 14: Tohoku Rakuten Golden Eagles 3 - 2 Hokkaido Nippon-Ham Fighters", // Moyen
                "Match 15: Orange Flyers 5 - 3 Purple Strikers",// Inconnu
                "Match 16: Rays 6 - 5 Blue Jays",             // Célèbre
                "Match 17: Tokyo Yakult Swallows 4 - 4 Chiba Lotte Marines", // Moyen
                "Match 18: Brown Homers 7 - 1 Gray Runners",   // Inconnu
                "Match 19: Phillies 3 - 2 Nationals",         // Célèbre
                "Match 20: Yomiuri Giants 5 - 3 Osaka Kintetsu Buffaloes", // Moyen
                "Match 21: Teal Swingers 6 - 5 Navy Bats"     // Inconnu
        });
        staticScores.put("🏏 Cricket", new String[]{
                "Match 1: India 245/5 (50) - Australia 230/8 (50)",        // Célèbre
                "Match 2: Otago Volts 300/7 (50) - Canterbury Kings 290/9 (50)", // Moyen
                "Match 3: Plains XI 320/6 (50) - Hills XI 310/8 (50)",    // Inconnu
                "Match 4: England 280/5 (50) - Pakistan 275/10 (49.3)",   // Célèbre
                "Match 5: Northern Knights 260/8 (50) - Auckland Aces 255/9 (50)", // Moyen
                "Match 6: River Bats 240/7 (50) - Lake Bowlers 235/10 (49.5)", // Inconnu
                "Match 7: South Africa 290/6 (50) - New Zealand 285/10 (49.2)", // Célèbre
                "Match 8: Wellington Firebirds 245/5 (50) - Central Stags 230/8 (50)", // Moyen
                "Match 9: Forest Stumps 300/7 (50) - Valley Wickets 290/9 (50)", // Inconnu
                "Match 10: West Indies 320/6 (50) - Sri Lanka 310/8 (50)", // Célèbre
                "Match 11: Tasmania Tigers 280/5 (50) - Victoria Bushrangers 275/10 (49.3)", // Moyen
                "Match 12: Coastal Sixers 260/8 (50) - Mountain Spinners 255/9 (50)", // Inconnu
                "Match 13: Bangladesh 240/7 (50) - Afghanistan 235/10 (49.5)", // Célèbre
                "Match 14: Queensland Bulls 290/6 (50) - NSW Blues 285/10 (49.2)", // Moyen
                "Match 15: City Pacers 245/5 (50) - Town Sloggers 230/8 (50)", // Inconnu
                "Match 16: Ireland 300/7 (50) - Zimbabwe 290/9 (50)",    // Célèbre
                "Match 17: South Australia Redbacks 320/6 (50) - Western Warriors 310/8 (50)", // Moyen
                "Match 18: Prairie Kings 280/5 (50) - Desert Riders 275/10 (49.3)", // Inconnu
                "Match 19: Scotland 260/8 (50) - Netherlands 255/9 (50)", // Célèbre
                "Match 20: Durham Dynamos 240/7 (50) - Sussex Sharks 235/10 (49.5)", // Moyen
                "Match 21: Urban Legends 290/6 (50) - Rural Giants 285/10 (49.2)" // Inconnu
        });
        staticScores.put("🏉 Rugby", new String[]{
                "Match 1: New Zealand 24 - 18 South Africa",          // Célèbre
                "Match 2: Toyota Verblitz 30 - 25 Kubota Spears",    // Moyen
                "Match 3: Northern Bulls 28 - 22 Southern Rams",    // Inconnu
                "Match 4: England 35 - 30 Australia",              // Célèbre
                "Match 5: Saitama Wild Knights 20 - 15 Kobelco Steelers", // Moyen
                "Match 6: Eastern Titans 40 - 38 Western Hawks",   // Inconnu
                "Match 7: France 19 - 17 Ireland",                // Célèbre
                "Match 8: Munster Rugby 24 - 18 Connacht Rugby",  // Moyen
                "Match 9: Central Wolves 30 - 25 Coastal Bears",  // Inconnu
                "Match 10: Wales 28 - 22 Scotland",              // Célèbre
                "Match 11: Glasgow Warriors 35 - 30 Edinburgh Rugby", // Moyen
                "Match 12: Highland Chiefs 20 - 15 Lowland Stags", // Inconnu
                "Match 13: Argentina 40 - 38 Japan",            // Célèbre
                "Match 14: Benetton Treviso 19 - 17 Zebre Parma", // Moyen
                "Match 15: Plains Chargers 24 - 18 Valley Rhinos", // Inconnu
                "Match 16: Fiji 30 - 25 Samoa",                // Célèbre
                "Match 17: Ospreys 28 - 22 Scarlets",          // Moyen
                "Match 18: Island Sharks 35 - 30 Bay Lions",   // Inconnu
                "Match 19: Italy 24 - 18 Georgia",            // Célèbre
                "Match 20: Dragons RFC 20 - 15 Cardiff Rugby", // Moyen
                "Match 21: City Knights 19 - 17 Town Falcons" // Inconnu
        });


        staticScores.put("⛳ Golf", new String[]{
                "Tournament 1: Tiger Woods - Score: -12",            // Célèbre
                "Tournament 2: Matt Wallace - Score: -10",          // Moyen
                "Tournament 3: Mark Evans - Score: -9",            // Inconnu
                "Tournament 4: Rory McIlroy - Score: -8",          // Célèbre
                "Tournament 5: Ryan Fox - Score: -7",             // Moyen
                "Tournament 6: James Carter - Score: -6",         // Inconnu
                "Tournament 7: Jordan Spieth - Score: -5",        // Célèbre
                "Tournament 8: Rasmus Højgaard - Score: -11",     // Moyen
                "Tournament 9: Tom Hayes - Score: -4",           // Inconnu
                "Tournament 10: Dustin Johnson - Score: -13",    // Célèbre
                "Tournament 11: Eddie Pepperell - Score: -9",    // Moyen
                "Tournament 12: Luke Parker - Score: -8",        // Inconnu
                "Tournament 13: Justin Thomas - Score: -10",     // Célèbre
                "Tournament 14: Thorbjørn Olesen - Score: -6",   // Moyen
                "Tournament 15: Ben Stone - Score: -7",          // Inconnu
                "Tournament 16: Brooks Koepka - Score: -12",     // Célèbre
                "Tournament 17: Laurie Canter - Score: -5",      // Moyen
                "Tournament 18: Ryan Ford - Score: -11",         // Inconnu
                "Tournament 19: Phil Mickelson - Score: -9",     // Célèbre
                "Tournament 20: Marcus Armitage - Score: -8",    // Moyen
                "Tournament 21: Sam Brooks - Score: -10"         // Inconnu
        });
        staticScores.put("🏓 Table Tennis", new String[]{
                "Match 1: Ma Long 4 - 2 Fan Zhendong",              // Célèbre
                "Match 2: Quadri Aruna 4 - 1 Omar Assar",          // Moyen
                "Match 3: Leo Chang 4 - 3 Kai Lin",               // Inconnu
                "Match 4: Xu Xin 4 - 2 Lin Gaoyuan",             // Célèbre
                "Match 5: Liam Pitchford 4 - 1 Simon Gauzy",     // Moyen
                "Match 6: Jin Park 4 - 0 Han Wei",              // Inconnu
                "Match 7: Dimitrij Ovtcharov 4 - 3 Timo Boll",   // Célèbre
                "Match 8: Mattias Falck 4 - 2 Vladimir Samsonov",// Moyen
                "Match 9: Max Vogel 4 - 1 Otto Blum",           // Inconnu
                "Match 10: Tomokazu Harimoto 4 - 2 Hugo Calderano", // Célèbre
                "Match 11: Kanak Jha 4 - 1 Sharath Kamal",      // Moyen
                "Match 12: Ken Sato 4 - 3 Raul Costa",         // Inconnu
                "Match 13: Jun Mizutani 4 - 2 Koki Niwa",     // Célèbre
                "Match 14: Jonathan Groth 4 - 1 Kirill Skachkov", // Moyen
                "Match 15: Ian Mori 4 - 0 Tim Naka",          // Inconnu
                "Match 16: Liam Pitchford 4 - 3 Quadri Aruna", // Célèbre
                "Match 17: Panagiotis Gionis 4 - 2 Álvaro Robles", // Moyen
                "Match 18: Eli Reed 4 - 1 Omar Khan",         // Inconnu
                "Match 19: Marcos Freitas 4 - 2 Daniel Habesohn", // Célèbre
                "Match 20: Mattias Falck 4 - 1 Patrick Franziska", // Moyen
                "Match 21: Lars Berg 4 - 3 Paul Fritz"        // Inconnu
        });
        staticScores.put("🏸 Badminton", new String[]{
                "Match 1: Kento Momota 21-18 21-15 Viktor Axelsen",      // Célèbre
                "Match 2: Hans-Kristian Vittinghus 21-19 21-17 Rajiv Ouseph", // Moyen
                "Match 3: Hiro Tan 21-16 21-14 Vikram Sen",             // Inconnu
                "Match 4: Chou Tien-chen 21-13 21-19 Anders Antonsen",  // Célèbre
                "Match 5: Liew Daren 21-12 21-10 Wong Wing Ki",        // Moyen
                "Match 6: Chen Wu 21-18 21-16 Arjun Patel",           // Inconnu
                "Match 7: Anthony Ginting 21-14 21-17 Lee Zii Jia",   // Célèbre
                "Match 8: Sameer Verma 21-19 21-15 Prannoy Kumar",    // Moyen
                "Match 9: Taro Kim 21-13 21-19 Liam Ong",            // Inconnu
                "Match 10: Chen Long 21-12 21-10 Shi Yuqi",         // Célèbre
                "Match 11: Hu Yun 21-18 21-16 Son Wan-ho",          // Moyen
                "Match 12: Wei Zhang 21-14 21-17 Jun Li",          // Inconnu
                "Match 13: Tai Tzu-ying 21-19 21-15 Carolina Marin", // Célèbre
                "Match 14: Mia Blichfeldt 21-13 21-19 Line Kjærsfeldt", // Moyen
                "Match 15: Mei Lin 21-12 21-10 Sara Cruz",         // Inconnu
                "Match 16: Nozomi Okuhara 21-18 21-16 P.V. Sindhu", // Célèbre
                "Match 17: Kirsty Gilmour 21-14 21-17 Neslihan Yiğit", // Moyen
                "Match 18: Aya Noda 21-19 21-15 Priya Rao",        // Inconnu
                "Match 19: Akane Yamaguchi 21-13 21-19 Ratchanok Intanon", // Célèbre
                "Match 20: Fitriani Fitriani 21-12 21-10 Gregoria Mariska", // Moyen
                "Match 21: Kana Yuki 21-18 21-16 Lena Suri"        // Inconnu
        });
        staticScores.put("🤾 Handball", new String[]{
                "Match 1: France 28 - 24 Denmark",                   // Célèbre
                "Match 2: Tatabánya KC 30 - 25 Balatonfüredi KSE",  // Moyen
                "Match 3: Blue Waves 32 - 28 Red Flames",          // Inconnu
                "Match 4: Spain 35 - 30 Croatia",                 // Célèbre
                "Match 5: IFK Kristianstad 19 - 17 Ystads IF",    // Moyen
                "Match 6: Green Arrows 24 - 18 Yellow Stars",     // Inconnu
                "Match 7: Germany 40 - 38 Sweden",               // Célèbre
                "Match 8: GWD Minden 28 - 22 Bergischer HC",     // Moyen
                "Match 9: Black Eagles 30 - 25 White Tigers",    // Inconnu
                "Match 10: Norway 19 - 17 Brazil",              // Célèbre
                "Match 11: Skjern Håndbold 35 - 30 GOG Håndbold", // Moyen
                "Match 12: Silver Storms 24 - 18 Bronze Giants", // Inconnu
                "Match 13: Egypt 28 - 22 Qatar",               // Célèbre
                "Match 14: Al Ahly SC 20 - 15 Zamalek SC",     // Moyen
                "Match 15: Gold Sands 32 - 28 Purple Peaks",   // Inconnu
                "Match 16: Russia 24 - 18 Poland",            // Célèbre
                "Match 17: Dinamo Viktor 30 - 25 Chekhovskiye Medvedi", // Moyen
                "Match 18: Orange Sparks 35 - 30 Pink Thunders", // Inconnu
                "Match 19: Iceland 28 - 22 Hungary",           // Célèbre
                "Match 20: HC Odorhei 19 - 17 Steaua București", // Moyen
                "Match 21: Teal Shadows 24 - 18 Gray Clouds"   // Inconnu
        });
        staticScores.put("🥊 Boxing", new String[]{
                "Fight 1: Canelo Alvarez def. Gennady Golovkin (UD)",      // Célèbre
                "Fight 2: David Lemieux def. Gary O’Sullivan (TKO)",      // Moyen
                "Fight 3: Jack Reed def. Tom Kane (UD)",                 // Inconnu
                "Fight 4: Tyson Fury def. Deontay Wilder (TKO)",         // Célèbre
                "Fight 5: Callum Smith def. John Ryder (SD)",           // Moyen
                "Fight 6: Mike Holt def. Sam Vale (TKO)",              // Inconnu
                "Fight 7: Anthony Joshua def. Oleksandr Usyk (UD)",    // Célèbre
                "Fight 8: Jermaine Franklin def. Charles Martin (UD)", // Moyen
                "Fight 9: Luke Gray def. Ben Cole (SD)",             // Inconnu
                "Fight 10: Manny Pacquiao def. Keith Thurman (SD)",  // Célèbre
                "Fight 11: Luis Nery def. Aaron Alameda (TKO)",     // Moyen
                "Fight 12: Dan West def. Joe Hart (UD)",           // Inconnu
                "Fight 13: Errol Spence Jr. def. Shawn Porter (SD)", // Célèbre
                "Fight 14: Erik Bazinyan def. Scott Sigmon (TKO)",  // Moyen
                "Fight 15: Eli Ford def. Max Dunn (UD)",          // Inconnu
                "Fight 16: Terence Crawford def. Kell Brook (TKO)", // Célèbre
                "Fight 17: Otto Wallin def. Travis Kauffman (SD)", // Moyen
                "Fight 18: Ray King def. Ian Bell (TKO)",        // Inconnu
                "Fight 19: Vasyl Lomachenko def. Teofimo Lopez (UD)", // Célèbre
                "Fight 20: Chris Algieri def. Mikkel LesPierre (TKO)", // Moyen
                "Fight 21: Vic Lane def. Tim Ross (UD)"         // Inconnu
        });
        staticScores.put("🤼 Wrestling", new String[]{
                "Match 1: USA 5 - 3 Russia",                     // Célèbre
                "Match 2: Poland 4 - 2 Ukraine",                // Moyen
                "Match 3: Northern Grapplers 6 - 5 Southern Pins", // Inconnu
                "Match 4: Iran 7 - 1 Turkey",                  // Célèbre
                "Match 5: Georgia 3 - 2 Armenia",             // Moyen
                "Match 6: Eastern Holds 8 - 6 Western Locks", // Inconnu
                "Match 7: Japan 4 - 4 Mongolia",             // Célèbre
                "Match 8: Bulgaria 5 - 3 Romania",          // Moyen
                "Match 9: Coastal Throws 6 - 5 Valley Mats", // Inconnu
                "Match 10: Georgia 7 - 1 Azerbaijan",       // Célèbre
                "Match 11: Hungary 3 - 2 Moldova",         // Moyen
                "Match 12: Highland Slams 4 - 2 Lowland Grips", // Inconnu
                "Match 13: India 5 - 3 Kazakhstan",       // Célèbre
                "Match 14: Serbia 6 - 5 Slovakia",        // Moyen
                "Match 15: Plains Wrestlers 7 - 1 River Tossers", // Inconnu
                "Match 16: Cuba 4 - 4 Ukraine",          // Célèbre
                "Match 17: Belarus 8 - 6 Lithuania",     // Moyen
                "Match 18: Island Brawlers 5 - 3 Bay Fighters", // Inconnu
                "Match 19: Germany 6 - 5 Bulgaria",     // Célèbre
                "Match 20: Latvia 4 - 2 Estonia",       // Moyen
                "Match 21: Urban Clinchers 7 - 1 Rural Takedowns" // Inconnu
        });
        staticScores.put("🥋 Judo", new String[]{
                "Match 1: Shohei Ono def. Hifumi Abe (Ippon)",          // Célèbre
                "Match 2: An Chang-rim def. Rustam Orujov (Waza-ari)", // Moyen
                "Match 3: Ken Ito def. Ryu Sato (Ippon)",             // Inconnu
                "Match 4: Teddy Riner def. Hisayoshi Harasawa (Ippon)", // Célèbre
                "Match 5: Guusje Steenhuis def. Maria Altheman (Waza-ari)", // Moyen
                "Match 6: Hugo Blanc def. Paul Noir (Ippon)",        // Inconnu
                "Match 7: Clarisse Agbegnenou def. Tina Trstenjak (Ippon)", // Célèbre
                "Match 8: Miku Tashiro def. Odette Giuffrida (Waza-ari)", // Moyen
                "Match 9: Lena Roy def. Mia Bleu (Ippon)",          // Inconnu
                "Match 10: Daria Bilodid def. Funa Tonaki (Ippon)", // Célèbre
                "Match 11: Distria Krasniqi def. Julia Figueroa (Waza-ari)", // Moyen
                "Match 12: Sara Kim def. Hana Lee (Ippon)",        // Inconnu
                "Match 13: Lukas Krpalek def. Tamerlan Bashaev (Ippon)", // Célèbre
                "Match 14: Sarah Asahina def. Larisa Cerić (Waza-ari)", // Moyen
                "Match 15: Jan Boer def. Tim Vos (Ippon)",        // Inconnu
                "Match 16: Uta Abe def. Amandine Buchard (Ippon)", // Célèbre
                "Match 17: Nikoloz Sherazadishvili def. Ivan Felipe Silva (Waza-ari)", // Moyen
                "Match 18: Aya Mori def. Nia Cruz (Ippon)",      // Inconnu
                "Match 19: Mashu Baker def. Aaron Wolf (Ippon)", // Célèbre
                "Match 20: Matthias Casse def. Saeid Mollaei (Waza-ari)", // Moyen
                "Match 21: Kai Berg def. Leo Falk (Ippon)"      // Inconnu
        });
        staticScores.put("🥋 Karate", new String[]{
                "Match 1: Ryo Kiyuna def. Damian Quintero (5-0)",      // Célèbre
                "Match 2: Ali Sofuoğlu def. Emre Vefa Göktaş (3-2)",  // Moyen
                "Match 3: Riku Naka def. Juan Toro (4-1)",          // Inconnu
                "Match 4: Sandra Sanchez def. Miho Miyahara (5-0)",  // Célèbre
                "Match 5: Viviana Bottaro def. Grace Lau (3-2)",    // Moyen
                "Match 6: Lena Fuji def. Mia Sano (4-1)",          // Inconnu
                "Match 7: Steven Da Costa def. Luigi Busa (5-0)",  // Célèbre
                "Match 8: Dastonbek Otabolaev def. Jesús Pérez (3-2)", // Moyen
                "Match 9: Taro Bosc def. Luca Riva (4-1)",        // Inconnu
                "Match 10: Serap Ozcelik def. Wen Tzu-yun (5-0)", // Célèbre
                "Match 11: Xiaoyan Yin def. Jasmin Jüttner (3-2)", // Moyen
                "Match 12: Sara Chen def. Wei Ling (4-1)",       // Inconnu
                "Match 13: Rafael Aghayev def. Douglas Brose (5-0)", // Célèbre
                "Match 14: Stanislav Horuna def. Noah Bitsch (3-2)", // Moyen
                "Match 15: Jiro Kato def. Pedro Lima (4-1)",    // Inconnu
                "Match 16: Ayumi Uekusa def. Meltem Hocaoğlu (5-0)", // Célèbre
                "Match 17: Elena Quirici def. Silvia Semeraro (3-2)", // Moyen
                "Match 18: Yumi Hara def. Aylin Kaya (4-1)",   // Inconnu
                "Match 19: Anzhelika Terliuga def. Jovana Prekovic (5-0)", // Célèbre
                "Match 20: Alexandra Recchia def. Merve Çoban (3-2)", // Moyen
                "Match 21: Nia Soto def. Eva Pohl (4-1)"       // Inconnu
        });
        staticScores.put("🥋 Taekwondo", new String[]{
                "Match 1: Jang Jun def. Kim Tae-hun (25-20)",          // Célèbre
                "Match 2: Bradly Sinden def. Carlos Sansores (30-25)", // Moyen
                "Match 3: Jin Ho def. Tae Min (28-22)",              // Inconnu
                "Match 4: Lee Dae-hoon def. Zhao Shuai (35-30)",     // Célèbre
                "Match 5: Nikita Glasnović def. Lauren Williams (20-15)", // Moyen
                "Match 6: Lee Jun def. Han Soo (40-38)",            // Inconnu
                "Match 7: Jade Jones def. Kim So-hee (19-17)",      // Célèbre
                "Match 8: Milica Mandić def. Bianca Walkden (25-20)", // Moyen
                "Match 9: Mia Park def. Soo Jin (30-25)",          // Inconnu
                "Match 10: Panipak Wongpattanakit def. Wu Jingyu (28-22)", // Célèbre
                "Match 11: Cansel Deniz def. Magda Wiet-Hénin (35-30)", // Moyen
                "Match 12: Aom Wong def. Lin Yu (20-15)",         // Inconnu
                "Match 13: Milad Beigi def. Aaron Cook (40-38)",  // Célèbre
                "Match 14: Joel González def. Jesús Tortosa (19-17)", // Moyen
                "Match 15: Reza Bahar def. Eli Cook (25-20)",    // Inconnu
                "Match 16: Simone Alessio def. Dmitriy Shokin (30-25)", // Célèbre
                "Match 17: Maxime Potvin def. Rúben Gonçalves (28-22)", // Moyen
                "Match 18: Luca Rossi def. Ivan Popov (35-30)", // Inconnu
                "Match 19: Svetlana Igumenova def. Tijana Bogdanovic (20-15)", // Célèbre
                "Match 20: Hatice Kübra İlgün def. Skylar Park (40-38)", // Moyen
                "Match 21: Lara Volk def. Tina Berg (19-17)"    // Inconnu
        });
        staticScores.put("🥋 Mixed Martial Arts", new String[]{
                "Fight 1: Khabib Nurmagomedov def. Justin Gaethje (Submission)", // Célèbre
                "Fight 2: Yaroslav Amosov def. Logan Storley (UD)",     // Moyen
                "Fight 3: Zane Holt def. Cole Nash (TKO)",            // Inconnu
                "Fight 4: Israel Adesanya def. Paulo Costa (KO)",     // Célèbre
                "Fight 5: A.J. McKee def. Darrion Caldwell (Submission)", // Moyen
                "Fight 6: Rex Ward def. Finn Dale (UD)",             // Inconnu
                "Fight 7: Amanda Nunes def. Felicia Spencer (TKO)",  // Célèbre
                "Fight 8: Juliana Velasquez def. Ilima-Lei Macfarlane (SD)", // Moyen
                "Fight 9: Tara Lin def. Jade Fox (TKO)",            // Inconnu
                "Fight 10: Jon Jones def. Dominick Reyes (UD)",    // Célèbre
                "Fight 11: Douglas Lima def. Rory MacDonald (TKO)", // Moyen
                "Fight 12: Jace King def. Owen Reed (Submission)", // Inconnu
                "Fight 13: Stipe Miocic def. Daniel Cormier (UD)", // Célèbre
                "Fight 14: Gegard Mousasi def. John Salter (SD)",  // Moyen
                "Fight 15: Milo Grant def. Seth Cain (TKO)",      // Inconnu
                "Fight 16: Zhang Weili def. Joanna Jedrzejczyk (UD)", // Célèbre
                "Fight 17: Denise Kielholtz def. Kate Jackson (KO)", // Moyen
                "Fight 18: Nia Chen def. Lila Hart (Submission)",  // Inconnu
                "Fight 19: Conor McGregor def. Donald Cerrone (TKO)", // Célèbre
                "Fight 20: Aaron Pico def. John de Jesus (UD)",    // Moyen
                "Fight 21: Kade Voss def. Ty Bell (TKO)"          // Inconnu
        });
        staticScores.put("🏊 Swimming", new String[]{
                "Event 1: Caeleb Dressel - 100m Freestyle: 47.02s",      // Célèbre
                "Event 2: Gregorio Paltrinieri - 100m Breaststroke: 57.37s", // Moyen
                "Event 3: Eli Wade - 800m Freestyle: 8:04.79",          // Inconnu
                "Event 4: Adam Peaty - 100m Butterfly: 55.48s",         // Célèbre
                "Event 5: Nicolo Martinenghi - 200m Freestyle: 1:44.39", // Moyen
                "Event 6: Tom Reef - 50m Freestyle: 24.05s",           // Inconnu
                "Event 7: Katie Ledecky - 200m Backstroke: 1:53.57",  // Célèbre
                "Event 8: Siobhan Haughey - 100m Freestyle: 47.50s",  // Moyen
                "Event 9: Sara Tide - 100m Breaststroke: 58.12s",     // Inconnu
                "Event 10: Sarah Sjöström - 800m Freestyle: 8:10.23", // Célèbre
                "Event 11: Anna Hopkin - 100m Butterfly: 56.78s",     // Moyen
                "Event 12: Lena Wave - 200m Freestyle: 1:45.90",     // Inconnu
                "Event 13: Sun Yang - 50m Freestyle: 24.33s",        // Célèbre
                "Event 14: Kliment Kolesnikov - 200m Backstroke: 1:54.45", // Moyen
                "Event 15: Kai Shore - 100m Freestyle: 48.10s",     // Inconnu
                "Event 16: Simone Manuel - 100m Breaststroke: 59.01s", // Célèbre
                "Event 17: Anastasia Fesikova - 800m Freestyle: 8:15.67", // Moyen
                "Event 18: Mia Surf - 100m Butterfly: 57.23s",     // Inconnu
                "Event 19: Ryan Murphy - 200m Freestyle: 1:46.78", // Célèbre
                "Event 20: Hugo González - 50m Freestyle: 24.89s", // Moyen
                "Event 21: Rex Pool - 200m Backstroke: 1:55.34"   // Inconnu
        });
        staticScores.put("🤽 Water Polo", new String[]{
                "Match 1: Hungary 10 - 8 Serbia",                   // Célèbre
                "Match 2: VK Jug Dubrovnik 12 - 11 CN Barceloneta", // Moyen
                "Match 3: Aqua Blues 9 - 7 Wave Reds",            // Inconnu
                "Match 4: Croatia 8 - 6 Italy",                  // Célèbre
                "Match 5: Pro Recco 14 - 13 Olympiacos Piraeus", // Moyen
                "Match 6: Splash Greens 10 - 9 Tide Yellows",    // Inconnu
                "Match 7: Spain 11 - 10 Greece",                // Célèbre
                "Match 8: Ferencvaros 7 - 5 Spandau 04",       // Moyen
                "Match 9: Ripple Whites 8 - 6 Current Blacks",  // Inconnu
                "Match 10: USA 9 - 7 Montenegro",             // Célèbre
                "Match 11: Jadran Split 10 - 8 Brescia",      // Moyen
                "Match 12: Surge Silvers 11 - 10 Stream Golds", // Inconnu
                "Match 13: Russia 12 - 11 Japan",            // Célèbre
                "Match 14: Radnički Kragujevac 6 - 5 Szolnoki VSC", // Moyen
                "Match 15: Flow Purples 9 - 8 Drift Oranges", // Inconnu
                "Match 16: Australia 14 - 13 Brazil",        // Célèbre
                "Match 17: VK Partizan 8 - 7 Waspo Hannover", // Moyen
                "Match 18: Crest Teals 10 - 9 Foam Browns",  // Inconnu
                "Match 19: France 7 - 5 Germany",           // Célèbre
                "Match 20: NC Vouliagmeni 11 - 10 Dinamo Moscow", // Moyen
                "Match 21: Spray Grays 8 - 6 Swell Navies"  // Inconnu
        });
        staticScores.put("🏊‍♂️ Diving", new String[]{
                "Event 1: Tom Daley - 10m Platform: 570.50",          // Célèbre
                "Event 2: Patrick Hausding - 3m Springboard: 558.75", // Moyen
                "Event 3: Sam Cliff - 10m Platform: 383.50",         // Inconnu
                "Event 4: Cao Yuan - 3m Springboard: 586.20",        // Célèbre
                "Event 5: Wang Zongyuan - 10m Platform: 545.45",     // Moyen
                "Event 6: Leo Spring - 3m Springboard: 370.85",      // Inconnu
                "Event 7: Shi Tingmao - 10m Platform: 362.40",       // Célèbre
                "Event 8: Krysta Palmer - 3m Springboard: 570.50",   // Moyen
                "Event 9: Mia Dive - 10m Platform: 558.75",          // Inconnu
                "Event 10: Chen Aisen - 3m Springboard: 383.50",     // Célèbre
                "Event 11: Matty Lee - 10m Platform: 586.20",        // Moyen
                "Event 12: Kai Drop - 3m Springboard: 545.45",       // Inconnu
                "Event 13: Jack Laugher - 10m Platform: 370.85",     // Célèbre
                "Event 14: Evgeny Kuznetsov - 3m Springboard: 362.40", // Moyen
                "Event 15: Rex Jump - 10m Platform: 570.50",        // Inconnu
                "Event 16: Melissa Wu - 3m Springboard: 558.75",    // Célèbre
                "Event 17: Jennifer Abel - 10m Platform: 383.50",   // Moyen
                "Event 18: Tara Plunge - 3m Springboard: 586.20",   // Inconnu
                "Event 19: Lin Shan - 10m Platform: 545.45",        // Célèbre
                "Event 20: Anabelle Smith - 3m Springboard: 370.85", // Moyen
                "Event 21: Nia Fall - 10m Platform: 362.40"         // Inconnu
        });
        staticScores.put("🚣 Rowing", new String[]{
                "Event 1: Single Sculls - Oliver Zeidler: 6:40.12",      // Célèbre
                "Event 2: Single Sculls - Damir Martin: 6:08.90",       // Moyen
                "Event 3: Single Sculls - Max Oar: 5:32.03",           // Inconnu
                "Event 4: Double Sculls - France: 6:15.34",           // Célèbre
                "Event 5: Double Sculls - Romania: 5:24.31",         // Moyen
                "Event 6: Double Sculls - River Duo: 6:13.88",       // Inconnu
                "Event 7: Quadruple Sculls - Netherlands: 7:23.98",  // Célèbre
                "Event 8: Quadruple Sculls - Poland: 6:40.12",       // Moyen
                "Event 9: Quadruple Sculls - Lake Four: 6:08.90",    // Inconnu
                "Event 10: Coxless Pair - New Zealand: 5:32.03",     // Célèbre
                "Event 11: Coxless Pair - Croatia: 6:15.34",        // Moyen
                "Event 12: Coxless Pair - Stream Pair: 5:24.31",    // Inconnu
                "Event 13: Eight - Germany: 6:13.88",              // Célèbre
                "Event 14: Eight - Canada: 7:23.98",              // Moyen
                "Event 15: Eight - Tide Eight: 6:40.12",          // Inconnu
                "Event 16: Lightweight Double Sculls - Ireland: 6:08.90", // Célèbre
                "Event 17: Lightweight Double Sculls - Norway: 5:32.03", // Moyen
                "Event 18: Lightweight Double Sculls - Pond Two: 6:15.34", // Inconnu
                "Event 19: Single Sculls - Sanita Puspure: 5:24.31", // Célèbre
                "Event 20: Single Sculls - Emma Twigg: 6:13.88",    // Moyen
                "Event 21: Single Sculls - Ella Row: 7:23.98"       // Inconnu
        });
        staticScores.put("🏄 Surfing", new String[]{
                "Event 1: Gabriel Medina - Wave Score: 18.93",        // Célèbre
                "Event 2: Owen Wright - Wave Score: 18.34",          // Moyen
                "Event 3: Zane Wave - Wave Score: 17.50",           // Inconnu
                "Event 4: Italo Ferreira - Wave Score: 16.89",      // Célèbre
                "Event 5: Jack Robinson - Wave Score: 16.45",       // Moyen
                "Event 6: Kai Crest - Wave Score: 15.98",          // Inconnu
                "Event 7: Carissa Moore - Wave Score: 15.75",      // Célèbre
                "Event 8: Sally Fitzgibbons - Wave Score: 18.93",  // Moyen
                "Event 9: Tara Surf - Wave Score: 18.34",         // Inconnu
                "Event 10: John John Florence - Wave Score: 17.50", // Célèbre
                "Event 11: Morgan Cibilic - Wave Score: 16.89",   // Moyen
                "Event 12: Rex Tide - Wave Score: 16.45",        // Inconnu
                "Event 13: Stephanie Gilmore - Wave Score: 15.98", // Célèbre
                "Event 14: Brisa Hennessy - Wave Score: 15.75",  // Moyen
                "Event 15: Mia Swell - Wave Score: 18.93",       // Inconnu
                "Event 16: Kolohe Andino - Wave Score: 18.34",   // Célèbre
                "Event 17: Ryan Callinan - Wave Score: 17.50",   // Moyen
                "Event 18: Leo Break - Wave Score: 16.89",       // Inconnu
                "Event 19: Kelly Slater - Wave Score: 16.45",    // Célèbre
                "Event 20: Tyler Wright - Wave Score: 15.98",    // Moyen
                "Event 21: Sam Foam - Wave Score: 15.75"         // Inconnu
        });
        staticScores.put("⛵ Sailing", new String[]{
                "Event 1: Laser - Robert Scheidt: 1st Place",           // Célèbre
                "Event 2: Laser - Pavlos Kontides: 1st Place",         // Moyen
                "Event 3: Laser - Tim Hull: 1st Place",               // Inconnu
                "Event 4: 49er - Peter Burling & Blair Tuke: 1st Place", // Célèbre
                "Event 5: 49er - Dylan Fletcher & Stuart Bithell: 1st Place", // Moyen
                "Event 6: 49er - Wind Twins: 1st Place",             // Inconnu
                "Event 7: Finn - Giles Scott: 1st Place",           // Célèbre
                "Event 8: Finn - Max Salminen: 1st Place",         // Moyen
                "Event 9: Finn - Rex Sail: 1st Place",            // Inconnu
                "Event 10: Nacra 17 - Ruggero Tita & Caterina Banti: 1st Place", // Célèbre
                "Event 11: Nacra 17 - John Gimson & Anna Burnet: 1st Place", // Moyen
                "Event 12: Nacra 17 - Breeze Pair: 1st Place",    // Inconnu
                "Event 13: RS:X - Kiran Badloe: 1st Place",       // Célèbre
                "Event 14: RS:X - Thomas Goyard: 1st Place",     // Moyen
                "Event 15: RS:X - Kai Gust: 1st Place",          // Inconnu
                "Event 16: 470 - Hannah Mills & Eilidh McIntyre: 1st Place", // Célèbre
                "Event 17: 470 - Jordi Xammar & Nora Brugman: 1st Place", // Moyen
                "Event 18: 470 - Wave Duo: 1st Place",          // Inconnu
                "Event 19: Kiteboarding - Nico Parlier: 1st Place", // Célèbre
                "Event 20: Kiteboarding - Daniela Moroz: 1st Place", // Moyen
                "Event 21: Kiteboarding - Sky Solo: 1st Place"  // Inconnu
        });
        staticScores.put("🛶 Kayaking", new String[]{
                "Event 1: K1 200m - Lisa Carrington: 39.39s",         // Célèbre
                "Event 2: K1 200m - Marta Walczykiewicz: 39.45s",    // Moyen
                "Event 3: K1 200m - Mia Paddle: 39.50s",            // Inconnu
                "Event 4: K1 1000m - Josef Dostál: 3:22.34",        // Célèbre
                "Event 5: K1 1000m - Fernando Pimenta: 3:23.12",    // Moyen
                "Event 6: K1 1000m - Leo Stroke: 3:24.56",         // Inconnu
                "Event 7: K2 500m - Germany: 1:28.95",             // Célèbre
                "Event 8: K2 500m - Hungary: 1:29.34",            // Moyen
                "Event 9: K2 500m - River Pair: 1:30.12",         // Inconnu
                "Event 10: K4 500m - Hungary: 1:19.82",          // Célèbre
                "Event 11: K4 500m - Slovakia: 1:20.45",         // Moyen
                "Event 12: K4 500m - Lake Squad: 1:21.78",       // Inconnu
                "Event 13: C1 200m - Ivan Shtyl: 39.45s",        // Célèbre
                "Event 14: C1 200m - Li Qiang: 39.50s",          // Moyen
                "Event 15: C1 200m - Rex Canoe: 39.55s",         // Inconnu
                "Event 16: C2 1000m - Germany: 3:30.12",        // Célèbre
                "Event 17: C2 1000m - Ukraine: 3:31.23",        // Moyen
                "Event 18: C2 1000m - Stream Duo: 3:32.45",     // Inconnu
                "Event 19: C1 1000m - Sebastian Brendel: 3:47.89", // Célèbre
                "Event 20: C1 1000m - Martin Fuksa: 3:48.56",   // Moyen
                "Event 21: C1 1000m - Kai Blade: 3:49.12"       // Inconnu
        });
        staticScores.put("⛷️ Skiing", new String[]{
                "Event 1: Slalom - Marcel Hirscher: 1:38.45",         // Célèbre
                "Event 2: Slalom - Clément Noël: 1:39.12",          // Moyen
                "Event 3: Slalom - Max Slope: 1:40.23",            // Inconnu
                "Event 4: Giant Slalom - Alexis Pinturault: 2:18.34", // Célèbre
                "Event 5: Giant Slalom - Filip Zubčić: 2:19.45",    // Moyen
                "Event 6: Giant Slalom - Leo Peak: 2:20.56",       // Inconnu
                "Event 7: Downhill - Beat Feuz: 1:40.12",          // Célèbre
                "Event 8: Downhill - Matthias Mayer: 1:41.23",     // Moyen
                "Event 9: Downhill - Rex Ridge: 1:42.34",         // Inconnu
                "Event 10: Super-G - Kjetil Jansrud: 1:19.82",    // Célèbre
                "Event 11: Super-G - Vincent Kriechmayr: 1:20.45", // Moyen
                "Event 12: Super-G - Kai Frost: 1:21.56",        // Inconnu
                "Event 13: Alpine Combined - Marco Schwarz: 2:30.45", // Célèbre
                "Event 14: Alpine Combined - Loïc Meillard: 2:31.12", // Moyen
                "Event 15: Alpine Combined - Sam Snow: 2:32.23", // Inconnu
                "Event 16: Cross-Country - Johannes Høsflot Klæbo: 3:47.89", // Célèbre
                "Event 17: Cross-Country - Alexander Bolshunov: 3:48.56", // Moyen
                "Event 18: Cross-Country - Tim Glide: 3:49.67",  // Inconnu
                "Event 19: Freestyle - Mikaël Kingsbury: 86.45",  // Célèbre
                "Event 20: Freestyle - Perrine Laffont: 85.12",   // Moyen
                "Event 21: Freestyle - Zane Flip: 84.23"         // Inconnu
        });
        staticScores.put("🏂 Snowboarding", new String[]{
                "Event 1: Halfpipe - Shaun White: 97.75",            // Célèbre
                "Event 2: Halfpipe - Scotty James: 96.45",          // Moyen
                "Event 3: Halfpipe - Rex Pipe: 95.12",             // Inconnu
                "Event 4: Slopestyle - Mark McMorris: 94.75",      // Célèbre
                "Event 5: Slopestyle - Chris Corning: 93.45",     // Moyen
                "Event 6: Slopestyle - Leo Shred: 92.12",         // Inconnu
                "Event 7: Big Air - Max Parrot: 91.75",           // Célèbre
                "Event 8: Big Air - Anna Gasser: 90.45",          // Moyen
                "Event 9: Big Air - Kai Spin: 89.12",            // Inconnu
                "Event 10: Parallel Giant Slalom - Ester Ledecká: 1:19.82", // Célèbre
                "Event 11: Parallel Giant Slalom - Roland Fischnaller: 1:20.45", // Moyen
                "Event 12: Parallel Giant Slalom - Mia Board: 1:21.56", // Inconnu
                "Event 13: Snowboard Cross - Pierre Vaultier: 1:30.45", // Célèbre
                "Event 14: Snowboard Cross - Alessandro Hämmerle: 1:31.12", // Moyen
                "Event 15: Snowboard Cross - Tim Rush: 1:32.23",  // Inconnu
                "Event 16: Freestyle - Chloe Kim: 94.75",        // Célèbre
                "Event 17: Freestyle - Zoi Sadowski-Synnott: 93.45", // Moyen
                "Event 18: Freestyle - Tara Twist: 92.12",       // Inconnu
                "Event 19: Alpine - Benjamin Karl: 1:47.89",     // Célèbre
                "Event 20: Alpine - Andrey Sobolev: 1:48.56",    // Moyen
                "Event 21: Alpine - Sam Slide: 1:49.67"          // Inconnu
        });
        staticScores.put("🏒 Ice Hockey", new String[]{
                "Match 1: Canada 4 - 3 USA",                     // Célèbre
                "Match 2: HC Davos 5 - 2 EV Zug",               // Moyen
                "Match 3: Frost Blades 3 - 1 Ice Stingers",     // Inconnu
                "Match 4: Russia 6 - 5 Sweden",                // Célèbre
                "Match 5: Frölunda HC 4 - 2 Luleå HF",         // Moyen
                "Match 6: Snow Wolves 3 - 2 Glacier Bears",    // Inconnu
                "Match 7: Finland 2 - 1 Czech Republic",       // Célèbre
                "Match 8: Tappara 5 - 3 HIFK",                // Moyen
                "Match 9: Chill Hawks 4 - 2 Freeze Eagles",    // Inconnu
                "Match 10: Switzerland 3 - 2 Germany",        // Célèbre
                "Match 11: ZSC Lions 6 - 5 Genève-Servette",  // Moyen
                "Match 12: Polar Caps 2 - 1 Rink Ravens",     // Inconnu
                "Match 13: Slovakia 4 - 3 Norway",           // Célèbre
                "Match 14: HC Slovan Bratislava 3 - 2 HC Košice", // Moyen
                "Match 15: Icy Sharks 5 - 4 Cold Tigers",    // Inconnu
                "Match 16: Latvia 6 - 5 Denmark",           // Célèbre
                "Match 17: Dinamo Riga 2 - 1 Jokerit",      // Moyen
                "Match 18: Frostbite Lions 4 - 3 Blizzard Owls", // Inconnu
                "Match 19: Belarus 3 - 2 France",          // Célèbre
                "Match 20: HC Vityaz 5 - 4 Avtomobilist Yekaterinburg", // Moyen
                "Match 21: Winter Foxes 2 - 1 Chill Panthers" // Inconnu
        });
        staticScores.put("⛸️ Figure Skating", new String[]{
                "Event 1: Men's Singles - Yuzuru Hanyu: 300.45",         // Célèbre
                "Event 2: Men's Singles - Jason Brown: 299.12",         // Moyen
                "Event 3: Men's Singles - Kai Spin: 298.23",           // Inconnu
                "Event 4: Ladies' Singles - Alina Zagitova: 239.57",   // Célèbre
                "Event 5: Ladies' Singles - Kaori Sakamoto: 238.45",  // Moyen
                "Event 6: Ladies' Singles - Mia Grace: 237.34",       // Inconnu
                "Event 7: Pairs - Wenjing Sui & Cong Han: 234.89",    // Célèbre
                "Event 8: Pairs - Anastasia Mishina & Aleksandr Galliamov: 233.56", // Moyen
                "Event 9: Pairs - Ice Duo: 232.45",                  // Inconnu
                "Event 10: Ice Dance - Gabriella Papadakis & Guillaume Cizeron: 226.45", // Célèbre
                "Event 11: Ice Dance - Madison Hubbell & Zachary Donohue: 225.12", // Moyen
                "Event 12: Ice Dance - Frost Pair: 224.23",         // Inconnu
                "Event 13: Team Event - Russia: 74 points",         // Célèbre
                "Event 14: Team Event - Japan: 73 points",         // Moyen
                "Event 15: Team Event - Snow Team: 72 points",     // Inconnu
                "Event 16: Men's Free Skate - Nathan Chen: 215.45", // Célèbre
                "Event 17: Men's Free Skate - Keegan Messing: 214.12", // Moyen
                "Event 18: Men's Free Skate - Rex Glide: 213.23",  // Inconnu
                "Event 19: Ladies' Free Skate - Evgenia Medvedeva: 210.45", // Célèbre
                "Event 20: Ladies' Free Skate - Loena Hendrickx: 209.12", // Moyen
                "Event 21: Ladies' Free Skate - Tara Twirl: 208.23" // Inconnu
        });
        staticScores.put("⏩ Speed Skating", new String[]{
                "Event 1: 500m - Pavel Kulizhnikov: 34.45s",          // Célèbre
                "Event 2: 500m - Laurent Dubreuil: 34.56s",         // Moyen
                "Event 3: 500m - Leo Dash: 34.67s",                // Inconnu
                "Event 4: 1000m - Kjeld Nuis: 1:07.34",            // Célèbre
                "Event 5: 1000m - Thomas Krol: 1:07.45",          // Moyen
                "Event 6: 1000m - Kai Speed: 1:07.56",            // Inconnu
                "Event 7: 1500m - Denis Yuskov: 1:42.12",         // Célèbre
                "Event 8: 1500m - Patrick Roest: 1:42.23",        // Moyen
                "Event 9: 1500m - Rex Pace: 1:42.34",            // Inconnu
                "Event 10: 5000m - Sven Kramer: 6:06.82",        // Célèbre
                "Event 11: 5000m - Nils van der Poel: 6:07.45",  // Moyen
                "Event 12: 5000m - Tim Ice: 6:08.12",           // Inconnu
                "Event 13: 10000m - Ted-Jan Bloemen: 12:45.89", // Célèbre
                "Event 14: 10000m - Graeme Fish: 12:46.56",    // Moyen
                "Event 15: 10000m - Sam Frost: 12:47.23",      // Inconnu
                "Event 16: Mass Start - Lee Seung-hoon: 7:47.89", // Célèbre
                "Event 17: Mass Start - Bart Swings: 7:48.56",  // Moyen
                "Event 18: Mass Start - Zane Rush: 7:49.23",   // Inconnu
                "Event 19: Team Pursuit - Netherlands: 3:37.45", // Célèbre
                "Event 20: Team Pursuit - Norway: 3:38.12",    // Moyen
                "Event 21: Team Pursuit - Chill Trio: 3:39.23" // Inconnu
        });
        staticScores.put("🥌 Curling", new String[]{
                "Match 1: Canada 8 - 7 Sweden",                   // Célèbre
                "Match 2: Team Hasselborg 6 - 5 Team Tirinzoni", // Moyen
                "Match 3: Rock Sliders 9 - 8 Stone Sweepers",    // Inconnu
                "Match 4: USA 7 - 6 Norway",                    // Célèbre
                "Match 5: Team Gushue 5 - 4 Team Edin",         // Moyen
                "Match 6: Ice Rollers 8 - 7 Frost Tossers",     // Inconnu
                "Match 7: Switzerland 9 - 8 Scotland",          // Célèbre
                "Match 8: Team Muirhead 6 - 5 Team Kovaleva",   // Moyen
                "Match 9: Curl Kings 7 - 6 Rink Riders",        // Inconnu
                "Match 10: China 8 - 7 Japan",                 // Célèbre
                "Match 11: Team Fujisawa 9 - 8 Team Homan",    // Moyen
                "Match 12: Broom Brigade 6 - 5 Sheet Stars",   // Inconnu
                "Match 13: Russia 7 - 6 Denmark",             // Célèbre
                "Match 14: Team Shuster 5 - 4 Team Bottcher", // Moyen
                "Match 15: Skip Squad 8 - 7 Ice Aces",        // Inconnu
                "Match 16: Great Britain 9 - 8 Germany",     // Célèbre
                "Match 17: Team Jones 6 - 5 Team Einarson",  // Moyen
                "Match 18: Stone Spinners 7 - 6 Rink Rebels", // Inconnu
                "Match 19: South Korea 8 - 7 Italy",        // Célèbre
                "Match 20: Team Roth 9 - 8 Team Sinclair",  // Moyen
                "Match 21: Sweep Legends 6 - 5 Curl Champs" // Inconnu
        });
        staticScores.put("🏎️ Formula One", new String[]{
                "Race 1: Lewis Hamilton - 1st Place",            // Célèbre
                "Race 2: Esteban Ocon - 1st Place",             // Moyen
                "Race 3: Jack Speed - 1st Place",              // Inconnu
                "Race 4: Max Verstappen - 1st Place",          // Célèbre
                "Race 5: Pierre Gasly - 1st Place",           // Moyen
                "Race 6: Max Torque - 1st Place",            // Inconnu
                "Race 7: Valtteri Bottas - 1st Place",       // Célèbre
                "Race 8: George Russell - 1st Place",        // Moyen
                "Race 9: Leo Drift - 1st Place",            // Inconnu
                "Race 10: Charles Leclerc - 1st Place",    // Célèbre
                "Race 11: Yuki Tsunoda - 1st Place",       // Moyen
                "Race 12: Sam Gear - 1st Place",          // Inconnu
                "Race 13: Sebastian Vettel - 1st Place",  // Célèbre
                "Race 14: Nicholas Latifi - 1st Place",   // Moyen
                "Race 15: Rex Wing - 1st Place",         // Inconnu
                "Race 16: Daniel Ricciardo - 1st Place", // Célèbre
                "Race 17: Sergio Pérez - 1st Place",    // Moyen
        });
        staticScores.put("🏍️ MotoGP", new String[]{
                "Race 1: Marc Márquez - 1st Place",
                "Race 2: Valentino Rossi - 1st Place",
                "Race 3: Maverick Viñales - 1st Place",
                "Race 4: Fabio Quartararo - 1st Place",
                "Race 5: Andrea Dovizioso - 1st Place",
                "Race 6: Joan Mir - 1st Place",
                "Race 7: Jack Miller - 1st Place",
                "Race 8: Miguel Oliveira - 1st Place",
                "Race 9: Francesco Bagnaia - 1st Place",
                "Race 10: Aleix Espargaró - 1st Place",
                "Race 11: Enea Bastianini - 1st Place",
                "Race 12: Johann Zarco - 1st Place",
                "Race 13: Brad Binder - 1st Place"
        });
        staticScores.put("🚗 Rally", new String[]{
                "Race 1: Sébastien Ogier - 1st Place",
                "Race 2: Ott Tänak - 1st Place",
                "Race 3: Thierry Neuville - 1st Place",
                "Race 4: Elfyn Evans - 1st Place",
                "Race 5: Kalle Rovanperä - 1st Place",
                "Race 6: Dani Sordo - 1st Place",
                "Race 7: Craig Breen - 1st Place",
                "Race 8: Gus Greensmith - 1st Place",
                "Race 9: Adrien Fourmaux - 1st Place",
                "Race 10: Teemu Suninen - 1st Place",
                "Race 11: Andreas Mikkelsen - 1st Place",
                "Race 12: Mads Østberg - 1st Place",
                "Race 13: Oliver Solberg - 1st Place"
        });
        staticScores.put("🏁 NASCAR", new String[]{
                "Race 1: Kyle Busch - 1st Place",
                "Race 2: Kevin Harvick - 1st Place",
                "Race 3: Martin Truex Jr. - 1st Place",
                "Race 4: Chase Elliott - 1st Place",
                "Race 5: Denny Hamlin - 1st Place",
                "Race 6: Brad Keselowski - 1st Place",
                "Race 7: Joey Logano - 1st Place",
                "Race 8: Austin Cindric - 1st Place",
                "Race 9: Tyler Reddick - 1st Place",
                "Race 10: William Byron - 1st Place",
                "Race 11: Christopher Bell - 1st Place",
                "Race 12: Alex Bowman - 1st Place",
                "Race 13: Ryan Blaney - 1st Place"
        });
        staticScores.put("🏃 Athletics", new String[]{
                "Event 1: 100m - Usain Bolt: 9.58s",
                "Event 2: 200m - Usain Bolt: 19.19s",
                "Event 3: 400m - Wayde van Niekerk: 43.03s",
                "Event 4: 800m - David Rudisha: 1:40.91",
                "Event 5: 1500m - Hicham El Guerrouj: 3:26.00",
                "Event 6: Marathon - Eliud Kipchoge: 2:01:39",
                "Event 7: Long Jump - Mike Powell: 8.95m",
                "Event 8: 200m - Noah Lyles: 19.19s",
                "Event 9: 400m - Michael Norman: 43.03s",
                "Event 10: 800m - Nijel Amos: 1:40.91",
                "Event 11: 1500m - Jakob Ingebrigtsen: 3:26.00",
                "Event 12: Marathon - Abdi Nageeye: 2:01:39",
                "Event 13: Long Jump - Miltiadis Tentoglou: 8.95m"
        });
        staticScores.put("🚴 Cycling", new String[]{
                "Event 1: Tour de France - Tadej Pogačar: 1st Place",
                "Event 2: Giro d'Italia - Egan Bernal: 1st Place",
                "Event 3: Vuelta a España - Primož Roglič: 1st Place",
                "Event 4: World Championships - Julian Alaphilippe: 1st Place",
                "Event 5: Paris-Roubaix - Mathieu van der Poel: 1st Place",
                "Event 6: Tour of Flanders - Kasper Asgreen: 1st Place",
                "Event 7: Milan-San Remo - Jasper Stuyven: 1st Place",
                "Event 8: Critérium du Dauphiné - Mark Padun: 1st Place",
                "Event 9: Tour de Pologne - João Almeida: 1st Place",
                "Event 10: Benelux Tour - Tim Wellens: 1st Place",
                "Event 11: Paris-Tours - Arnaud Démare: 1st Place",
                "Event 12: Tour de Wallonie - Quinn Simmons: 1st Place",
                "Event 13: Clásica San Sebastián - Neilson Powless: 1st Place"
        });
        staticScores.put("🤸 Gymnastics", new String[]{
                "Event 1: All-Around - Simone Biles: 58.700",
                "Event 2: Floor Exercise - Simone Biles: 15.133",
                "Event 3: Vault - Simone Biles: 15.400",
                "Event 4: Uneven Bars - Nina Derwael: 15.200",
                "Event 5: Balance Beam - Simone Biles: 14.633",
                "Event 6: Rings - Eleftherios Petrounias: 15.300",
                "Event 7: Parallel Bars - Zou Jingyuan: 16.000",
                "Event 8: Floor Exercise - Artem Dolgopyat: 15.133",
                "Event 9: Vault - Shin Jea-hwan: 15.400",
                "Event 10: Uneven Bars - Sunisa Lee: 15.200",
                "Event 11: Balance Beam - Guan Chenchen: 14.633",
                "Event 12: Rings - Liu Yang: 15.300",
                "Event 13: Parallel Bars - You Hao: 16.000"
        });
        staticScores.put("🏹 Archery", new String[]{
                "Event 1: Men's Individual - Brady Ellison: 1st Place",
                "Event 2: Women's Individual - Kang Chae-young: 1st Place",
                "Event 3: Men's Team - South Korea: 1st Place",
                "Event 4: Women's Team - South Korea: 1st Place",
                "Event 5: Mixed Team - South Korea: 1st Place",
                "Event 6: Recurve - Kim Woo-jin: 1st Place",
                "Event 7: Compound - Sara López: 1st Place",
                "Event 8: Women's Individual - An San: 1st Place",
                "Event 9: Men's Team - Chinese Taipei: 1st Place",
                "Event 10: Women's Team - Mexico: 1st Place",
                "Event 11: Mixed Team - Netherlands: 1st Place",
                "Event 12: Recurve - Steve Wijler: 1st Place",
                "Event 13: Compound - Mike Schloesser: 1st Place"
        });
        staticScores.put("🐎 Equestrian", new String[]{
                "Event 1: Dressage - Isabell Werth: 1st Place",
                "Event 2: Show Jumping - Steve Guerdat: 1st Place",
                "Event 3: Eventing - Michael Jung: 1st Place",
                "Event 4: Cross-Country - Oliver Townend: 1st Place",
                "Event 5: Team Dressage - Germany: 1st Place",
                "Event 6: Team Show Jumping - Sweden: 1st Place",
                "Event 7: Team Eventing - Great Britain: 1st Place",
                "Event 8: Show Jumping - Ben Maher: 1st Place",
                "Event 9: Eventing - Laura Collett: 1st Place",
                "Event 10: Cross-Country - Andrew Hoy: 1st Place",
                "Event 11: Team Dressage - Denmark: 1st Place",
                "Event 12: Team Show Jumping - USA: 1st Place",
                "Event 13: Team Eventing - Australia: 1st Place"
        });
        staticScores.put("🤺 Fencing", new String[]{
                "Event 1: Men's Foil - Daniele Garozzo: 1st Place",
                "Event 2: Women's Foil - Inna Deriglazova: 1st Place",
                "Event 3: Men's Sabre - Áron Szilágyi: 1st Place",
                "Event 4: Women's Sabre - Sofya Velikaya: 1st Place",
                "Event 5: Men's Épée - Romain Cannone: 1st Place",
                "Event 6: Women's Épée - Nathalie Moellhausen: 1st Place",
                "Event 7: Team Foil - USA: 1st Place",
                "Event 8: Women's Foil - Lee Kiefer: 1st Place",
                "Event 9: Men's Sabre - Oh Sang-uk: 1st Place",
                "Event 10: Women's Sabre - Olga Kharlan: 1st Place",
                "Event 11: Men's Épée - Gauthier Grumier: 1st Place",
                "Event 12: Women's Épée - Ana Maria Popescu: 1st Place",
                "Event 13: Team Foil - Italy: 1st Place"
        });
        staticScores.put("🏋️ Weightlifting", new String[]{
                "Event 1: Men's 61kg - Li Fabin: 1st Place",
                "Event 2: Women's 49kg - Hou Zhihui: 1st Place",
                "Event 3: Men's 73kg - Shi Zhiyong: 1st Place",
                "Event 4: Women's 59kg - Kuo Hsing-chun: 1st Place",
                "Event 5: Men's 81kg - Lü Xiaojun: 1st Place",
                "Event 6: Women's 64kg - Deng Wei: 1st Place",
                "Event 7: Men's 96kg - Sohrab Moradi: 1st Place",
                "Event 8: Women's 49kg - Mirabai Chanu: 1st Place",
                "Event 9: Men's 73kg - CJ Cummings: 1st Place",
                "Event 10: Women's 59kg - Hsing-Chun Kuo: 1st Place",
                "Event 11: Men's 81kg - Meso Hassouna: 1st Place",
                "Event 12: Women's 64kg - Maude Charron: 1st Place",
                "Event 13: Men's 96kg - Fares El-Bakh: 1st Place"
        });
        staticScores.put("🔫 Shooting", new String[]{
                "Event 1: Men's 10m Air Rifle - Yang Haoran: 1st Place",
                "Event 2: Women's 10m Air Rifle - Apurvi Chandela: 1st Place",
                "Event 3: Men's 50m Rifle 3 Positions - Zhang Changhong: 1st Place",
                "Event 4: Women's 50m Rifle 3 Positions - Nina Christen: 1st Place",
                "Event 5: Men's 10m Air Pistol - Saurabh Chaudhary: 1st Place",
                "Event 6: Women's 10m Air Pistol - Anna Korakaki: 1st Place",
                "Event 7: Mixed Team 10m Air Rifle - China: 1st Place",
                "Event 8: Women's 10m Air Rifle - Elavenil Valarivan: 1st Place",
                "Event 9: Men's 50m Rifle 3 Positions - Sergey Kamenskiy: 1st Place",
                "Event 10: Women's 50m Rifle 3 Positions - Seonaid McIntosh: 1st Place",
                "Event 11: Men's 10m Air Pistol - Jitu Rai: 1st Place",
                "Event 12: Women's 10m Air Pistol - Zorana Arunović: 1st Place",
                "Event 13: Mixed Team 10m Air Rifle - India: 1st Place"
        });
        staticScores.put("🛹 Skateboarding", new String[]{
                "Event 1: Men's Street - Yuto Horigome: 1st Place",
                "Event 2: Women's Street - Rayssa Leal: 1st Place",
                "Event 3: Men's Park - Keegan Palmer: 1st Place",
                "Event 4: Women's Park - Sakura Yosozumi: 1st Place",
                "Event 5: Men's Vert - Tony Hawk: 1st Place",
                "Event 6: Women's Vert - Lizzie Armanto: 1st Place",
                "Event 7: Men's Freestyle - Rodney Mullen: 1st Place",
                "Event 8: Women's Street - Aori Nishimura: 1st Place",
                "Event 9: Men's Park - Pedro Barros: 1st Place",
                "Event 10: Women's Park - Misugu Okamoto: 1st Place",
                "Event 11: Men's Vert - Jimmy Wilkins: 1st Place",
                "Event 12: Women's Vert - Jordyn Barratt: 1st Place",
                "Event 13: Men's Freestyle - Isamu Yamamoto: 1st Place"
        });
        staticScores.put("🧗 Climbing", new String[]{
                "Event 1: Men's Lead - Adam Ondra: 1st Place",
                "Event 2: Women's Lead - Janja Garnbret: 1st Place",
                "Event 3: Men's Speed - Bassa Mawem: 1st Place",
                "Event 4: Women's Speed - Aleksandra Mirosław: 1st Place",
                "Event 5: Men's Bouldering - Tomoa Narasaki: 1st Place",
                "Event 6: Women's Bouldering - Miho Nonaka: 1st Place",
                "Event 7: Combined - Alberto Ginés López: 1st Place",
                "Event 8: Women's Lead - Laura Rogora: 1st Place",
                "Event 9: Men's Speed - Ludovico Fossali: 1st Place",
                "Event 10: Women's Speed - Iuliia Kaplina: 1st Place",
                "Event 11: Men's Bouldering - Yoshiyuki Ogata: 1st Place",
                "Event 12: Women's Bouldering - Natalia Grossman: 1st Place",
                "Event 13: Combined - Sean McColl: 1st Place"
        });
        staticScores.put("🏊‍♀️ Triathlon", new String[]{
                "Event 1: Men's Individual - Kristian Blummenfelt: 1st Place",
                "Event 2: Women's Individual - Flora Duffy: 1st Place",
                "Event 3: Men's Relay - France: 1st Place",
                "Event 4: Women's Relay - USA: 1st Place",
                "Event 5: Mixed Relay - Great Britain: 1st Place",
                "Event 6: Sprint - Alex Yee: 1st Place",
                "Event 7: Ironman - Jan Frodeno: 1st Place",
                "Event 8: Women's Individual - Georgia Taylor-Brown: 1st Place",
                "Event 9: Men's Relay - Germany: 1st Place",
                "Event 10: Women's Relay - France: 1st Place",
                "Event 11: Mixed Relay - Netherlands: 1st Place",
                "Event 12: Sprint - Jonathan Brownlee: 1st Place",
                "Event 13: Ironman - Gustav Iden: 1st Place"
        });



        // Ajouter toutes les catégories à la liste
        categories.addAll(staticScores.keySet());
    }

    private void setupCategoryListView() {
        // Configurer la liste des catégories avec filtrage
        FilteredList<String> filteredCategories = new FilteredList<>(categories, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCategories.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return category.toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<String> sortedCategories = new SortedList<>(filteredCategories);
        categoryListView.setItems(sortedCategories);

        // Ajouter un écouteur pour afficher les scores de la catégorie sélectionnée
        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayScoresForCategory(newValue);
            }
        });
    }

    private void displayScoresForCategory(String category) {
        liveScoresContainer.getChildren().clear();
        String[] scores = staticScores.get(category);
        if (scores != null) {
            for (String score : scores) {
                // Créer une carte pour chaque score
                VBox card = createScoreCard(score);
                liveScoresContainer.getChildren().add(card);
            }
        }
    }

    private VBox createScoreCard(String score) {
        // Créer une carte stylisée
        VBox card = new VBox();
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Ajouter un titre (nom des équipes ou des joueurs)
        Label titleLabel = new Label(score.split(":")[0]);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Ajouter le score
        Label scoreLabel = new Label(score.split(":")[1].trim());
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #555555;");

        // Remplacer le temps par "En cours"
        Label timeLabel = new Label("En cours");
        timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #0077cc;");

        // Ajouter les éléments à la carte
        card.getChildren().addAll(titleLabel, scoreLabel, timeLabel);
        card.setSpacing(10);
        card.setAlignment(Pos.CENTER);

        return card;
    }


    private void updateScores() {
        // Simuler une mise à jour des scores
        for (Map.Entry<String, String[]> entry : staticScores.entrySet()) {
            String[] scores = entry.getValue();
            for (int i = 0; i < scores.length; i++) {
                scores[i] = scores[i].replaceAll("\\d", String.valueOf((int) (Math.random() * 10)));
            }
        }

        // Afficher les scores mis à jour
        String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            displayScoresForCategory(selectedCategory);
        }
    }

    @FXML
    private void goToMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) liveScoresContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
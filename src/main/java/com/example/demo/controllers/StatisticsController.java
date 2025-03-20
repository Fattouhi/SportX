package com.example.demo.controllers;

import com.example.demo.dao.BannedUsersDAO;
import com.example.demo.dao.DatabaseConnection;
import com.example.demo.dao.EventDAO;
import com.example.demo.dao.UsersDAO;
import com.example.demo.model.EventRequest;
import com.example.demo.model.Users;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class StatisticsController {

    @FXML private BarChart<String, Number> userStatsChart;
    @FXML private TableView<UserStat> userTable;
    @FXML private TableColumn<UserStat, String> userTotalCol;
    @FXML private TableColumn<UserStat, String> userActiveCol;
    @FXML private TableColumn<UserStat, String> userBannedCol;

    @FXML private BarChart<String, Number> eventStatsChart;
    @FXML private TableView<EventStat> eventTable;
    @FXML private TableColumn<EventStat, String> eventTotalCol;
    @FXML private TableColumn<EventStat, String> eventFutureCol; // Renommé pour "Futurs" au lieu de "Actifs"
    @FXML private TableColumn<EventStat, String> eventPastApprovedCol;
    @FXML private TableColumn<EventStat, String> eventPastPendingCol;
    @FXML private TableColumn<EventStat, String> eventPastRejectedCol;

    @FXML private PieChart eventApprovalChart;
    @FXML private TableView<ApprovalData> approvalTable;
    @FXML private TableColumn<ApprovalData, String> statusCol;
    @FXML private TableColumn<ApprovalData, String> countCol;
    @FXML private TableColumn<ApprovalData, String> percentageCol;

    @FXML private Label notificationLabel; // Label pour les notifications

    @FXML
    public void initialize() {
        try {
            loadUserStatistics();
            loadEventStatistics();
            loadEventApprovalStatistics();
        } catch (SQLException e) {
            e.printStackTrace();
            showNotification("Erreur", "Impossible de charger les statistiques : " + e.getMessage(), false);
        }
    }

    private void loadUserStatistics() throws SQLException {
        // Récupérer tous les utilisateurs
        List<Users> users = UsersDAO.getAllUsers();
        int totalUsers = users.size();

        // Compter les utilisateurs actifs et bannis en utilisant BannedUsersDAO
        int activeUsers = 0;
        int bannedUsers = 0;
        BannedUsersDAO bannedUsersDAO = new BannedUsersDAO(DatabaseConnection.getConnection());
        for (Users user : users) {
            if (bannedUsersDAO.isUserBanned(user.getUserId())) {
                bannedUsers++;
            } else {
                activeUsers++;
            }
        }

        // Configurer le tableau avec une seule ligne
        userTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        userActiveCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        userBannedCol.setCellValueFactory(new PropertyValueFactory<>("banned"));

        ObservableList<UserStat> userData = FXCollections.observableArrayList(
                new UserStat(String.valueOf(totalUsers), String.valueOf(activeUsers), String.valueOf(bannedUsers))
        );
        userTable.setItems(userData);

        // Configurer le graphique en barres
        userStatsChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Total", totalUsers));
        series.getData().add(new XYChart.Data<>("Actifs", activeUsers));
        series.getData().add(new XYChart.Data<>("Bannis", bannedUsers));
        userStatsChart.getData().add(series);

        // Ajouter les stylesheets avec vérification pour le débogage
        try {
            userStatsChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/style.css")).toExternalForm());
            System.out.println("Stylesheets chargés avec succès pour userStatsChart.");
        } catch (NullPointerException e) {
            System.err.println("Erreur : style.css non trouvé à /com/example/demo/View/style.css");
            e.printStackTrace();
        }
    }

    private void loadEventStatistics() throws SQLException {
        // Instancier EventDAO pour appeler getAllEventRequests()
        EventDAO eventDAO = new EventDAO();
        // Récupérer tous les événements
        List<EventRequest> events = eventDAO.getAllEventRequests();
        int totalEvents = events.size();
        LocalDate now = LocalDate.now();

        // Compter les événements futurs (approuvés avec date future)
        int futureEvents = (int) events.stream()
                .filter(event -> event.getStatus().equalsIgnoreCase("approved") && event.getEventDate().isAfter(now))
                .count();

        // Compter les événements passés par statut
        int pastApproved = (int) events.stream()
                .filter(event -> event.getStatus().equalsIgnoreCase("approved") && event.getEventDate().isBefore(now))
                .count();
        int pastPending = (int) events.stream()
                .filter(event -> event.getStatus().equalsIgnoreCase("pending") && event.getEventDate().isBefore(now))
                .count();
        int pastRejected = (int) events.stream()
                .filter(event -> event.getStatus().equalsIgnoreCase("rejected") && event.getEventDate().isBefore(now))
                .count();

        // Configurer le tableau avec une seule ligne
        eventTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        eventFutureCol.setCellValueFactory(new PropertyValueFactory<>("future"));
        eventPastApprovedCol.setCellValueFactory(new PropertyValueFactory<>("pastApproved"));
        eventPastPendingCol.setCellValueFactory(new PropertyValueFactory<>("pastPending"));
        eventPastRejectedCol.setCellValueFactory(new PropertyValueFactory<>("pastRejected"));

        ObservableList<EventStat> eventData = FXCollections.observableArrayList(
                new EventStat(String.valueOf(totalEvents), String.valueOf(futureEvents),
                        String.valueOf(pastApproved), String.valueOf(pastPending), String.valueOf(pastRejected))
        );
        eventTable.setItems(eventData);

        // Configurer le graphique en barres
        eventStatsChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Total", totalEvents));
        series.getData().add(new XYChart.Data<>("Futurs", futureEvents));
        series.getData().add(new XYChart.Data<>("Passés (Approuvés)", pastApproved));
        series.getData().add(new XYChart.Data<>("Passés (En Attente)", pastPending));
        series.getData().add(new XYChart.Data<>("Passés (Refusés)", pastRejected));
        eventStatsChart.getData().add(series);

        // Ajouter les stylesheets pour appliquer les couleurs via CSS
        eventStatsChart.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/style.css")).toExternalForm());    }

    private void loadEventApprovalStatistics() throws SQLException {
        // Instancier EventDAO pour appeler getAllEventRequests()
        EventDAO eventDAO = new EventDAO();
        // Récupérer tous les événements
        List<EventRequest> events = eventDAO.getAllEventRequests();
        int approvedCount = (int) events.stream().filter(event -> event.getStatus().equalsIgnoreCase("approved")).count();
        int rejectedCount = (int) events.stream().filter(event -> event.getStatus().equalsIgnoreCase("rejected")).count();
        int pendingCount = (int) events.stream().filter(event -> event.getStatus().equalsIgnoreCase("pending")).count();
        int totalEvents = events.size();

        // Configurer le tableau
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        ObservableList<ApprovalData> approvalData = FXCollections.observableArrayList(
                new ApprovalData("Approuvé", String.valueOf(approvedCount), String.format("%.1f%%", (approvedCount * 100.0) / totalEvents)),
                new ApprovalData("Refusé", String.valueOf(rejectedCount), String.format("%.1f%%", (rejectedCount * 100.0) / totalEvents)),
                new ApprovalData("En Attente", String.valueOf(pendingCount), String.format("%.1f%%", (pendingCount * 100.0) / totalEvents))
        );
        approvalTable.setItems(approvalData);

        // Configurer le graphique circulaire
        eventApprovalChart.getData().clear();
        eventApprovalChart.getData().add(new PieChart.Data("Approuvé", approvedCount));
        eventApprovalChart.getData().add(new PieChart.Data("Refusé", rejectedCount));
        eventApprovalChart.getData().add(new PieChart.Data("En Attente", pendingCount));
    }

    @FXML
    private void goToDashboard() {
        try {
            Stage stage = (Stage) userStatsChart.getScene().getWindow();
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/dashboard-view.fxml")));
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showNotification("Erreur", "Impossible de retourner au tableau de bord : " + e.getMessage(), false);
        }
    }

    @FXML
    private void refreshStatistics() {
        try {
            // Rafraîchir toutes les statistiques
            loadUserStatistics();
            loadEventStatistics();
            loadEventApprovalStatistics();
            showNotification("Succès", "Statistiques rafraîchies avec succès !", true);
        } catch (SQLException e) {
            e.printStackTrace();
            showNotification("Erreur", "Impossible de rafraîchir les statistiques : " + e.getMessage(), false);
        }
    }

    private void showNotification(String title, String message, boolean isSuccess) {
        if (notificationLabel == null) {
            System.err.println("❌ Erreur : notificationLabel est null !");
            return;
        }

        // Définir le texte et le style du message
        notificationLabel.setText(title + " : " + message);
        notificationLabel.setStyle("-fx-background-color: " + (isSuccess ? "rgba(0, 128, 0, 0.7)" : "rgba(255, 0, 0, 0.7)") +
                "; -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 5px;");

        // Positionner le label au centre de la fenêtre
        notificationLabel.setLayoutX((userStatsChart.getScene().getWidth() - notificationLabel.getWidth()) / 2);
        notificationLabel.setLayoutY(100); // Position Y fixe (ajustez selon vos besoins)

        // Rendre le label visible
        notificationLabel.setVisible(true);

        // Animation de fondu pour l'apparition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), notificationLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Animation de fondu pour la disparition après 3 secondes
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), notificationLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(3)); // Afficher pendant 3 secondes
        fadeOut.setOnFinished(event -> notificationLabel.setVisible(false));
        fadeOut.play();
    }

    // Classe pour les données du tableau des utilisateurs
    public static class UserStat {
        private final String total;
        private final String active;
        private final String banned;

        public UserStat(String total, String active, String banned) {
            this.total = total;
            this.active = active;
            this.banned = banned;
        }

        public String getTotal() { return total; }
        public String getActive() { return active; }
        public String getBanned() { return banned; }
    }

    // Classe pour les données du tableau des événements
    public static class EventStat {
        private final String total;
        private final String future;
        private final String pastApproved;
        private final String pastPending;
        private final String pastRejected;

        public EventStat(String total, String future, String pastApproved, String pastPending, String pastRejected) {
            this.total = total;
            this.future = future;
            this.pastApproved = pastApproved;
            this.pastPending = pastPending;
            this.pastRejected = pastRejected;
        }

        public String getTotal() { return total; }
        public String getFuture() { return future; }
        public String getPastApproved() { return pastApproved; }
        public String getPastPending() { return pastPending; }
        public String getPastRejected() { return pastRejected; }
    }

    // Classe pour les données du tableau des événements approuvés/refusés
    public static class ApprovalData {
        private final String status;
        private final String count;
        private final String percentage;

        public ApprovalData(String status, String count, String percentage) {
            this.status = status;
            this.count = count;
            this.percentage = percentage;
        }

        public String getStatus() { return status; }
        public String getCount() { return count; }
        public String getPercentage() { return percentage; }
    }
}
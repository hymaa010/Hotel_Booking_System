import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Map;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Hotel Booking System");
        primaryStage.setScene(createMainMenu());
        primaryStage.show();
    }

    private Scene createMainMenu() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label title = new Label("Welcome to Hotel Booking System");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Button guestBtn = new Button("Guest");
        Button adminBtn = new Button("Admin");
        Button exitBtn = new Button("Exit");

        // Set button sizes
        guestBtn.setPrefWidth(150);
        adminBtn.setPrefWidth(150);
        exitBtn.setPrefWidth(150);

        guestBtn.setOnAction(e -> primaryStage.setScene(createGuestMenu()));
        adminBtn.setOnAction(e -> primaryStage.setScene(createAdminLogin()));
        exitBtn.setOnAction(e -> System.exit(0));

        vbox.getChildren().addAll(title, guestBtn, adminBtn, exitBtn);

        return new Scene(vbox, 350, 300);
    }

    // === Guest Menu Scene ===
    private Scene createGuestMenu() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label title = new Label("Guest Menu");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Button signupBtn = new Button("Sign Up");
        Button loginBtn = new Button("Login");
        Button backBtn = new Button("Back");

        signupBtn.setPrefWidth(150);
        loginBtn.setPrefWidth(150);
        backBtn.setPrefWidth(150);

        signupBtn.setOnAction(e -> primaryStage.setScene(createSignupForm()));
        loginBtn.setOnAction(e -> primaryStage.setScene(createLoginForm()));
        backBtn.setOnAction(e -> primaryStage.setScene(createMainMenu()));

        vbox.getChildren().addAll(title, signupBtn, loginBtn, backBtn);

        return new Scene(vbox, 350, 250);
    }

    // === Sign Up Form Scene ===
    private Scene createSignupForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Guest Registration");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label emailLbl = new Label("Email:");
        TextField emailFld = new TextField();
        emailFld.setPromptText("Enter your email");

        Label nameLbl = new Label("Name:");
        TextField nameFld = new TextField();
        nameFld.setPromptText("Enter your full name");

        Label passLbl = new Label("Password:");
        PasswordField passFld = new PasswordField();
        passFld.setPromptText("Enter your password");

        Label phoneLbl = new Label("Phone:");
        TextField phoneFld = new TextField();
        phoneFld.setPromptText("Enter your phone number");

        Button submitBtn = new Button("Sign Up");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Add components to grid
        grid.add(title, 0, 0, 2, 1);
        grid.add(emailLbl, 0, 1);
        grid.add(emailFld, 1, 1);
        grid.add(nameLbl, 0, 2);
        grid.add(nameFld, 1, 2);
        grid.add(passLbl, 0, 3);
        grid.add(passFld, 1, 3);
        grid.add(phoneLbl, 0, 4);
        grid.add(phoneFld, 1, 4);
        grid.add(buttonBox, 0, 5, 2, 1);

        submitBtn.setOnAction(e -> {
            String email = emailFld.getText();
            String name = nameFld.getText();
            String password = passFld.getText();
            String phone = phoneFld.getText();

            // Input validation
            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                showAlert("Error", "All fields must be filled!");
                return;
            }
            // Phone number validation: must be all digits and 10-15 digits (customize as needed)
            if (!phone.matches("\\d{10,15}")) {
                showAlert("Error", "Invalid phone number. It must be 10-15 digits.");
                return;
            }

            try {
                Guest guest = new Guest();
                guest.signup(email, name, password, phone);
                showAlert("Success", "Signup successful!");
                primaryStage.setScene(createGuestMenu());
            } catch (IllegalArgumentException ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> primaryStage.setScene(createGuestMenu()));

        return new Scene(grid, 450, 350);
    }

    private Scene createLoginForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Guest Login");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label emailLbl = new Label("Email:");
        TextField emailFld = new TextField();
        emailFld.setPromptText("Enter your email");

        Label passLbl = new Label("Password:");
        PasswordField passFld = new PasswordField();
        passFld.setPromptText("Enter your password");

        Button loginBtn = new Button("Login");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(loginBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        grid.add(title, 0, 0, 2, 1);
        grid.add(emailLbl, 0, 1);
        grid.add(emailFld, 1, 1);
        grid.add(passLbl, 0, 2);
        grid.add(passFld, 1, 2);
        grid.add(buttonBox, 0, 3, 2, 1);

        loginBtn.setOnAction(e -> {
            String email = emailFld.getText();
            String password = passFld.getText();

            try {
                User user = Guest.login(email, password, false);
                if (user != null) {
                    primaryStage.setScene(createBookingMenu((Guest) user));
                } else {
                    showAlert("Error", "Invalid credentials.");
                }
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> primaryStage.setScene(createGuestMenu()));

        return new Scene(grid, 450, 250);
    }

    // === Booking Menu Scene ===
    private Scene createBookingMenu(Guest user) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label title = new Label("Welcome, " + user.getName() + "!");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Button reserveBtn = new Button("Reserve Room");
        Button hallBtn = new Button("Reserve Hall");
        Button logoutBtn = new Button("Logout");

        reserveBtn.setPrefWidth(200);
        hallBtn.setPrefWidth(200);
        logoutBtn.setPrefWidth(200);

        reserveBtn.setOnAction(e -> primaryStage.setScene(createBookingForm(user)));
        hallBtn.setOnAction(e -> primaryStage.setScene(createHallBookingForm(user)));
        logoutBtn.setOnAction(e -> primaryStage.setScene(createMainMenu()));

        vbox.getChildren().addAll(title, reserveBtn, hallBtn, logoutBtn);

        return new Scene(vbox, 350, 300);
    }

    // === Booking Form Scene ===
    private Scene createBookingForm(Guest user) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(18);
        grid.setPadding(new Insets(30, 40, 30, 40));
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Reserve a Room");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; margin-bottom: 10px;");

        Label section1 = new Label("Reservation Dates");
        section1.setStyle("-fx-font-size: 14; -fx-font-weight: bold; margin-top: 10px;");
        Label checkInLbl = new Label("Check-in Date:");
        DatePicker checkInPicker = new DatePicker(LocalDate.now());
        Label checkOutLbl = new Label("Check-out Date:");
        DatePicker checkOutPicker = new DatePicker(LocalDate.now().plusDays(1));

        Label section2 = new Label("Room Details");
        section2.setStyle("-fx-font-size: 14; -fx-font-weight: bold; margin-top: 10px;");
        Label companionsLbl = new Label("Number of Companions:");
        Spinner<Integer> companionsSpinner = new Spinner<>(0, 10, 0);
        companionsSpinner.setEditable(true);
        companionsSpinner.setPrefWidth(120);
        Label roomTypeLbl = new Label("Room Type:");
        ComboBox<String> roomTypeCombo = new ComboBox<>();
        roomTypeCombo.getItems().addAll("Single", "Double", "Suite");
        roomTypeCombo.setValue("Single");
        roomTypeCombo.setPrefWidth(120);
        Label availabilityLbl = new Label("Available rooms:");
        Label singleAvailLbl = new Label("Single: " + Room.getSingleRoom());
        Label doubleAvailLbl = new Label("Double: " + Room.getDoubleRoom());
        Label suiteAvailLbl = new Label("Suite: " + Room.getSuiteRoom());
        HBox availabilityBox = new HBox(15, singleAvailLbl, doubleAvailLbl, suiteAvailLbl);

        roomTypeCombo.setOnAction(e -> {
            singleAvailLbl.setText("Single: " + Room.getSingleRoom());
            doubleAvailLbl.setText("Double: " + Room.getDoubleRoom());
            suiteAvailLbl.setText("Suite: " + Room.getSuiteRoom());
        });

        Label section3 = new Label("Promo & Price");
        section3.setStyle("-fx-font-size: 14; -fx-font-weight: bold; margin-top: 10px;");
        Label promoLbl = new Label("Promo Code (Optional):");
        TextField promoField = new TextField();
        promoField.setPromptText("Enter promo code");
        promoField.setPrefWidth(120);
        Label priceLbl = new Label("Price:");
        Label priceValueLbl = new Label("$0.00");
        Button calcPriceBtn = new Button("Calculate Price");
        calcPriceBtn.setPrefWidth(140);

        Button submitBtn = new Button("Reserve");
        Button backBtn = new Button("Back");
        submitBtn.setPrefWidth(140);
        backBtn.setPrefWidth(140);
        HBox buttonBox = new HBox(20, submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        int row = 0;
        grid.add(title, 0, row++, 2, 1);
        grid.add(section1, 0, row++, 2, 1);
        grid.add(checkInLbl, 0, row); grid.add(checkInPicker, 1, row++);
        grid.add(checkOutLbl, 0, row); grid.add(checkOutPicker, 1, row++);
        grid.add(section2, 0, row++, 2, 1);
        grid.add(companionsLbl, 0, row); grid.add(companionsSpinner, 1, row++);
        grid.add(roomTypeLbl, 0, row); grid.add(roomTypeCombo, 1, row++);
        grid.add(availabilityLbl, 0, row); grid.add(availabilityBox, 1, row++);
        grid.add(section3, 0, row++, 2, 1);
        grid.add(promoLbl, 0, row); grid.add(promoField, 1, row++);
        grid.add(priceLbl, 0, row); grid.add(priceValueLbl, 1, row++);
        grid.add(calcPriceBtn, 1, row++);
        grid.add(buttonBox, 1, row++);

        // Calculate price action (no booking object created)
        calcPriceBtn.setOnAction(e -> {
            try {
                LocalDate checkInDate = checkInPicker.getValue();
                LocalDate checkOutDate = checkOutPicker.getValue();

                if (checkInDate == null || checkOutDate == null) {
                    showAlert("Error", "Please select check-in and check-out dates");
                    return;
                }

                if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
                    showAlert("Error", "Check-out date must be after check-in date");
                    return;
                }

                Room room = null;
                switch (roomTypeCombo.getValue()) {
                    case "Single":
                        if (Room.getSingleRoom() <= 0) {
                            showAlert("Error", "No single rooms available");
                            return;
                        }
                        room = new SingleRoom();
                        room.addRoom(); // Add back the room since we're just calculating
                        break;
                    case "Double":
                        if (Room.getDoubleRoom() <= 0) {
                            showAlert("Error", "No double rooms available");
                            return;
                        }
                        room = new DoubleRoom();
                        room.addRoom();
                        break;
                    case "Suite":
                        if (Room.getSuiteRoom() <= 0) {
                            showAlert("Error", "No suite rooms available");
                            return;
                        }
                        room = new Suite();
                        room.addRoom();
                        break;
                }

                long duration = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                double price = room.getPricePerNight() * duration;
                String promoCode = promoField.getText();

                if (!promoCode.isEmpty()) {
                    Double promoValue = Offer.getPromoValue(promoCode);
                    if (promoValue != null) {
                        price = (1 - promoValue) * price;
                    } else {
                        showAlert("Info", "Invalid promo code");
                    }
                }

                priceValueLbl.setText(String.format("$%.2f", price));

            } catch (Exception ex) {
                showAlert("Error", "Invalid input: " + ex.getMessage());
            }
        });

        // Submit booking (creates the booking object)
        submitBtn.setOnAction(e -> {
            try {
                LocalDate checkInDate = checkInPicker.getValue();
                LocalDate checkOutDate = checkOutPicker.getValue();
                int companions = companionsSpinner.getValue();
                String promoCode = promoField.getText();

                if (checkInDate == null || checkOutDate == null) {
                    showAlert("Error", "Please select check-in and check-out dates");
                    return;
                }

                if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
                    showAlert("Error", "Check-out date must be after check-in date");
                    return;
                }

                Room room = null;
                switch (roomTypeCombo.getValue()) {
                    case "Single":
                        if (Room.getSingleRoom() <= 0) {
                            showAlert("Error", "No single rooms available");
                            return;
                        }
                        room = new SingleRoom();
                        break;
                    case "Double":
                        if (Room.getDoubleRoom() <= 0) {
                            showAlert("Error", "No double rooms available");
                            return;
                        }
                        room = new DoubleRoom();
                        break;
                    case "Suite":
                        if (Room.getSuiteRoom() <= 0) {
                            showAlert("Error", "No suite rooms available");
                            return;
                        }
                        room = new Suite();
                        break;
                }

                // Create the booking object (add to bookings list)
                RoomBooking booking = new RoomBooking(user, companions, room, checkInDate, checkOutDate);
                double finalPrice = room.calculatePrice(booking);

                if (!promoCode.isEmpty()) {
                    Double promoValue = Offer.getPromoValue(promoCode);
                    if (promoValue != null) {
                        finalPrice = room.applyPromoCode(promoCode, booking);
                    }
                }

                // Create confirmation page
                VBox confirmationLayout = new VBox(15);
                confirmationLayout.setPadding(new Insets(20));
                confirmationLayout.setAlignment(Pos.CENTER);

                Label confirmationLabel = new Label("Reservation Confirmed!");
                confirmationLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

                VBox detailsBox = new VBox(5);
                detailsBox.getChildren().addAll(
                        new Label("Guest: " + user.getName()),
                        new Label("Room Type: " + room.getType()),
                        new Label("Check-in: " + checkInDate),
                        new Label("Check-out: " + checkOutDate),
                        new Label("Duration: " + booking.getDuration() + " nights"),
                        new Label("Companions: " + companions),
                        new Label("Final Price: $" + String.format("%.2f", finalPrice))
                );

                if (!promoCode.isEmpty() && Offer.getPromoValue(promoCode) != null) {
                    detailsBox.getChildren().add(new Label("Promo Code Applied: " + promoCode));
                }

                HBox confirmButtonBox = new HBox(10);
                Button bookAnotherBtn = new Button("Book Another Booking");
                Button logoutBtn = new Button("Logout");
                confirmButtonBox.getChildren().addAll(bookAnotherBtn, logoutBtn);
                confirmButtonBox.setAlignment(Pos.CENTER);

                confirmationLayout.getChildren().addAll(confirmationLabel, detailsBox, confirmButtonBox);

                Scene confirmationScene = new Scene(confirmationLayout, 450, 400);

                bookAnotherBtn.setOnAction(ev -> primaryStage.setScene(createBookingMenu(user)));
                logoutBtn.setOnAction(ev -> primaryStage.setScene(createMainMenu()));

                primaryStage.setScene(confirmationScene);

            } catch (Exception ex) {
                showAlert("Error", "Invalid Booking details: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> primaryStage.setScene(createBookingMenu(user)));

        return new Scene(grid, 600, 600);
    }

    // === Hall Booking Form Scene ===
    private Scene createHallBookingForm(Guest user) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(18);
        grid.setPadding(new Insets(30, 40, 30, 40));
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Book a Hall");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; margin-bottom: 10px;");

        Label section1 = new Label("Event Details");
        section1.setStyle("-fx-font-size: 14; -fx-font-weight: bold; margin-top: 10px;");
        Label hallTypeLbl = new Label("Hall Type:");
        ComboBox<String> hallTypeCombo = new ComboBox<>();
        hallTypeCombo.getItems().addAll("Wedding Hall", "Conference Hall");
        hallTypeCombo.setValue("Wedding Hall");
        hallTypeCombo.setPrefWidth(140);
        Label eventTypeLbl = new Label("Event Type:");
        TextField eventTypeField = new TextField();
        eventTypeField.setPromptText("e.g. Wedding, Meeting");
        eventTypeField.setPrefWidth(140);
        Label attendeesLbl = new Label("Number of Attendees:");
        Spinner<Integer> attendeesSpinner = new Spinner<>(1, 500, 1);
        attendeesSpinner.setEditable(true);
        attendeesSpinner.setPrefWidth(140);

        Label section2 = new Label("Date & Time");
        section2.setStyle("-fx-font-size: 14; -fx-font-weight: bold; margin-top: 10px;");
        Label startDateLbl = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        Label startHourLbl = new Label("Start Hour:");
        ComboBox<Integer> startHourCombo = new ComboBox<>();
        for (int i = 0; i < 24; i++) startHourCombo.getItems().add(i);
        startHourCombo.setValue(14);
        startHourCombo.setPrefWidth(140);
        Label durationLbl = new Label("Duration (hours):");
        ComboBox<Integer> durationCombo = new ComboBox<>();
        for (int i = 1; i <= 24; i++) durationCombo.getItems().add(i);
        durationCombo.setValue(4);
        durationCombo.setPrefWidth(140);

        Label section3 = new Label("Promo & Price");
        section3.setStyle("-fx-font-size: 14; -fx-font-weight: bold; margin-top: 10px;");
        Label promoLbl = new Label("Promo Code (Optional):");
        TextField promoField = new TextField();
        promoField.setPromptText("Enter promo code");
        promoField.setPrefWidth(140);
        Label priceLbl = new Label("Price:");
        Label priceValueLbl = new Label("$0.00");
        Button calcPriceBtn = new Button("Calculate Price");
        calcPriceBtn.setPrefWidth(140);

        Button submitBtn = new Button("Book Hall");
        Button backBtn = new Button("Back");
        submitBtn.setPrefWidth(140);
        backBtn.setPrefWidth(140);
        HBox buttonBox = new HBox(20, submitBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        int row = 0;
        grid.add(title, 0, row++, 2, 1);
        grid.add(section1, 0, row++, 2, 1);
        grid.add(hallTypeLbl, 0, row); grid.add(hallTypeCombo, 1, row++);
        grid.add(eventTypeLbl, 0, row); grid.add(eventTypeField, 1, row++);
        grid.add(attendeesLbl, 0, row); grid.add(attendeesSpinner, 1, row++);
        grid.add(section2, 0, row++, 2, 1);
        grid.add(startDateLbl, 0, row); grid.add(startDatePicker, 1, row++);
        grid.add(startHourLbl, 0, row); grid.add(startHourCombo, 1, row++);
        grid.add(durationLbl, 0, row); grid.add(durationCombo, 1, row++);
        grid.add(section3, 0, row++, 2, 1);
        grid.add(promoLbl, 0, row); grid.add(promoField, 1, row++);
        grid.add(priceLbl, 0, row); grid.add(priceValueLbl, 1, row++);
        grid.add(calcPriceBtn, 1, row++);
        grid.add(buttonBox, 1, row++);

        // Calculate price action (no booking object created)
        calcPriceBtn.setOnAction(e -> {
            try {
                String hallType = hallTypeCombo.getValue();
                LocalDate startDate = startDatePicker.getValue();
                int startHour = startHourCombo.getValue();
                int duration = durationCombo.getValue();
                if (startDate == null) {
                    showAlert("Error", "Please select start date");
                    return;
                }
                java.time.LocalDateTime startTime = java.time.LocalDateTime.of(startDate, java.time.LocalTime.of(startHour, 0));
                java.time.LocalDateTime endTime = startTime.plusHours(duration);
                Hall hall = null;
                switch (hallType) {
                    case "Wedding Hall":
                        if (Hall.getWeddingHall() <= 0) throw new HallUnavailableException("No wedding halls available");
                        hall = new WeddingHall();
                        hall.addHall(); // Add back for calculation
                        break;
                    case "Conference Hall":
                        if (Hall.getConferenceHall() <= 0) throw new HallUnavailableException("No conference halls available");
                        hall = new ConferenceHall();
                        hall.addHall();
                        break;
                }
                // Simulate duration in hours
                long durationHours = java.time.Duration.between(startTime, endTime).toHours();
                double price = hall.getPricePerHour() * durationHours;
                String promoCode = promoField.getText();
                if (!promoCode.isEmpty()) {
                    Double promoValue = Offer.getPromoValue(promoCode);
                    if (promoValue != null) {
                        price = (1 - promoValue) * price;
                    } else {
                        showAlert("Info", "Invalid promo code");
                    }
                }
                priceValueLbl.setText(String.format("$%.2f", price));
            } catch (HallUnavailableException ex) {
                showAlert("Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert("Error", "Invalid input: " + ex.getMessage());
            }
        });

        // Submit booking (creates the booking object)
        submitBtn.setOnAction(e -> {
            try {
                String hallType = hallTypeCombo.getValue();
                int attendees = attendeesSpinner.getValue();
                String eventType = eventTypeField.getText();
                LocalDate startDate = startDatePicker.getValue();
                int startHour = startHourCombo.getValue();
                int duration = durationCombo.getValue();
                if (startDate == null) {
                    showAlert("Error", "Please select start date");
                    return;
                }
                java.time.LocalDateTime startTime = java.time.LocalDateTime.of(startDate, java.time.LocalTime.of(startHour, 0));
                java.time.LocalDateTime endTime = startTime.plusHours(duration);
                Hall hall = null;
                switch (hallType) {
                    case "Wedding Hall":
                        if (Hall.getWeddingHall() <= 0) throw new HallUnavailableException("No wedding halls available");
                        hall = new WeddingHall();
                        break;
                    case "Conference Hall":
                        if (Hall.getConferenceHall() <= 0) throw new HallUnavailableException("No conference halls available");
                        hall = new ConferenceHall();
                        break;
                }
                HallBooking booking = new HallBooking(user, attendees, hall, startTime, endTime, eventType);
                double finalPrice = hall.calculatePrice(booking);
                String promoCode = promoField.getText();
                if (!promoCode.isEmpty()) {
                    Double promoValue = Offer.getPromoValue(promoCode);
                    if (promoValue != null) {
                        finalPrice = hall.applyPromoCode(promoCode, booking);
                    }
                }
                // Confirmation page
                VBox confirmationLayout = new VBox(15);
                confirmationLayout.setPadding(new Insets(20));
                confirmationLayout.setAlignment(Pos.CENTER);
                Label confirmationLabel = new Label("Hall Reservation Confirmed!");
                confirmationLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                VBox detailsBox = new VBox(5);
                detailsBox.getChildren().addAll(
                        new Label("Guest: " + user.getName()),
                        new Label("Hall Type: " + hall.getType()),
                        new Label("Event Type: " + eventType),
                        new Label("Start: " + startTime),
                        new Label("End: " + endTime),
                        new Label("Attendees: " + attendees),
                        new Label("Duration: " + booking.getDuration() + " hours"),
                        new Label("Final Price: $" + String.format("%.2f", finalPrice))
                );
                if (!promoCode.isEmpty() && Offer.getPromoValue(promoCode) != null) {
                    detailsBox.getChildren().add(new Label("Promo Code Applied: " + promoCode));
                }
                HBox confirmButtonBox = new HBox(10);
                Button bookAnotherBtn = new Button("Book Another Booking");
                Button logoutBtn = new Button("Logout");
                confirmButtonBox.getChildren().addAll(bookAnotherBtn, logoutBtn);
                confirmButtonBox.setAlignment(Pos.CENTER);
                confirmationLayout.getChildren().addAll(confirmationLabel, detailsBox, confirmButtonBox);
                Scene confirmationScene = new Scene(confirmationLayout, 450, 400);
                bookAnotherBtn.setOnAction(ev -> primaryStage.setScene(createBookingMenu(user)));
                logoutBtn.setOnAction(ev -> primaryStage.setScene(createMainMenu()));
                primaryStage.setScene(confirmationScene);

            } catch (HallUnavailableException ex) {
                showAlert("Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert("Error", "Invalid Booking details: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> primaryStage.setScene(createBookingMenu(user)));

        return new Scene(grid, 600, 600);
    }

    private Scene createAdminLogin() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Admin Login");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label emailLbl = new Label("Admin Email:");
        TextField emailFld = new TextField();
        emailFld.setPromptText("Enter admin email");

        Label passLbl = new Label("Admin Password:");
        PasswordField passFld = new PasswordField();
        passFld.setPromptText("Enter admin password");

        Button loginBtn = new Button("Login");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(loginBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        grid.add(title, 0, 0, 2, 1);
        grid.add(emailLbl, 0, 1);
        grid.add(emailFld, 1, 1);
        grid.add(passLbl, 0, 2);
        grid.add(passFld, 1, 2);
        grid.add(buttonBox, 0, 3, 2, 1);

        loginBtn.setOnAction(e -> {
            String email = emailFld.getText();
            String password = passFld.getText();

            try {
                User admin = Admin.login(email, password, true);
                if (admin != null) {
                    primaryStage.setScene(createAdminView(admin));
                } else {
                    showAlert("Error", "Invalid admin credentials.");
                }
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> primaryStage.setScene(createMainMenu()));

        return new Scene(grid, 450, 250);
    }

    private Scene createAdminView(User admin) {
        TabPane tabPane = new TabPane();

   
        Tab roomsTab = new Tab("Room Management");
        roomsTab.setClosable(false);

        GridPane roomsGrid = new GridPane();
        roomsGrid.setHgap(10);
        roomsGrid.setVgap(15);
        roomsGrid.setPadding(new Insets(20));

   
        Label roomStatusTitle = new Label("Current Room Status");
        roomStatusTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label singleLbl = new Label("Single Rooms: " + Room.getSingleRoom());
        Label doubleLbl = new Label("Double Rooms: " + Room.getDoubleRoom());
        Label suiteLbl = new Label("Suite Rooms: " + Room.getSuiteRoom());

       
        Label priceTitle = new Label("Update Room Prices");
        priceTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        ComboBox<String> roomTypeCombo = new ComboBox<>();
        roomTypeCombo.getItems().addAll("Single", "Double", "Suite");
        roomTypeCombo.setValue("Single");

        TextField priceField = new TextField();
        priceField.setPromptText("Enter new price");

        Button updatePriceBtn = new Button("Update Price");
        updatePriceBtn.setOnAction(e -> {
            try {
                double newPrice = Double.parseDouble(priceField.getText());
                if (newPrice <= 0) {
                    showAlert("Error", "Price must be positive");
                    return;
                }

                Admin adminObj = (Admin)admin;
                adminObj.roomPrice(roomTypeCombo.getValue(), newPrice);
                showAlert("Success", roomTypeCombo.getValue() + " room price updated to $" + newPrice);

            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid price");
            }
        });

        roomsGrid.add(roomStatusTitle, 0, 0, 2, 1);
        roomsGrid.add(singleLbl, 0, 1);
        roomsGrid.add(doubleLbl, 0, 2);
        roomsGrid.add(suiteLbl, 0, 3);

        roomsGrid.add(priceTitle, 0, 4, 2, 1);
        roomsGrid.add(new Label("Room Type:"), 0, 5);
        roomsGrid.add(roomTypeCombo, 1, 5);
        roomsGrid.add(new Label("New Price:"), 0, 6);
        roomsGrid.add(priceField, 1, 6);
        roomsGrid.add(updatePriceBtn, 1, 7);

        roomsTab.setContent(roomsGrid);

        Tab hallsTab = new Tab("Hall Management");
        hallsTab.setClosable(false);
        GridPane hallsGrid = new GridPane();
        hallsGrid.setHgap(10);
        hallsGrid.setVgap(15);
        hallsGrid.setPadding(new Insets(20));
        Label hallStatusTitle = new Label("Current Hall Status");
        hallStatusTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        Label weddingLbl = new Label("Wedding Halls: " + Hall.getWeddingHall());
        Label conferenceLbl = new Label("Conference Halls: " + Hall.getConferenceHall());
        Label hallPriceTitle = new Label("Update Hall Prices");
        hallPriceTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        ComboBox<String> hallTypeCombo = new ComboBox<>();
        hallTypeCombo.getItems().addAll("Wedding Hall", "Conference Hall");
        hallTypeCombo.setValue("Wedding Hall");
        TextField hallPriceField = new TextField();
        hallPriceField.setPromptText("Enter new price");
        Button updateHallPriceBtn = new Button("Update Price");
        updateHallPriceBtn.setOnAction(e -> {
            try {
                double newPrice = Double.parseDouble(hallPriceField.getText());
                if (newPrice <= 0) {
                    showAlert("Error", "Price must be positive");
                    return;
                }
                switch (hallTypeCombo.getValue()) {
                    case "Wedding Hall":
                        new WeddingHall().setPrice(newPrice);
                        break;
                    case "Conference Hall":
                        new ConferenceHall().setPrice(newPrice);
                        break;
                }
                showAlert("Success", hallTypeCombo.getValue() + " price updated to $" + newPrice);
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid price");
            }
        });
        hallsGrid.add(hallStatusTitle, 0, 0, 2, 1);
        hallsGrid.add(weddingLbl, 0, 1);
        hallsGrid.add(conferenceLbl, 0, 2);
        hallsGrid.add(hallPriceTitle, 0, 3, 2, 1);
        hallsGrid.add(new Label("Hall Type:"), 0, 4);
        hallsGrid.add(hallTypeCombo, 1, 4);
        hallsGrid.add(new Label("New Price:"), 0, 5);
        hallsGrid.add(hallPriceField, 1, 5);
        hallsGrid.add(updateHallPriceBtn, 1, 6);
        hallsTab.setContent(hallsGrid);

  
        Tab promoTab = new Tab("Promo Codes");
        promoTab.setClosable(false);

        VBox promoVBox = new VBox(15);
        promoVBox.setPadding(new Insets(20));

        Label promoTitle = new Label("Manage Promo Codes");
        promoTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label currentPromosLbl = new Label("Current Promo Codes:");
        ListView<String> promoListView = new ListView<>();

        
        for (Map.Entry<String, Double> entry : Offer.getPromoCodes().entrySet()) {
            promoListView.getItems().add(entry.getKey() + " - " +
                    (entry.getValue() * 100) + "% discount");
        }

        
        Label addPromoTitle = new Label("Add New Promo Code");
        GridPane addPromoGrid = new GridPane();
        addPromoGrid.setHgap(10);
        addPromoGrid.setVgap(10);

        TextField newPromoNameField = new TextField();
        newPromoNameField.setPromptText("Promo code name");

        TextField newPromoValueField = new TextField();
        newPromoValueField.setPromptText("Discount value (0-1)");

        Button addPromoBtn = new Button("Add Promo Code");

        addPromoGrid.add(new Label("Code:"), 0, 0);
        addPromoGrid.add(newPromoNameField, 1, 0);
        addPromoGrid.add(new Label("Value:"), 0, 1);
        addPromoGrid.add(newPromoValueField, 1, 1);
        addPromoGrid.add(addPromoBtn, 1, 2);
    
        Label removePromoLbl = new Label("Remove Promo Code");
        HBox removePromoBox = new HBox(10);

        TextField removePromoField = new TextField();
        removePromoField.setPromptText("Promo code to remove");

        Button removePromoBtn = new Button("Remove");

        removePromoBox.getChildren().addAll(removePromoField, removePromoBtn);

        
        addPromoBtn.setOnAction(e -> {
            try {
                String code = newPromoNameField.getText();
                String valueText = newPromoValueField.getText();

                if (code.isEmpty() || valueText.isEmpty()) {
                    showAlert("Error", "Please enter both code and value");
                    return;
                }

                double value = Double.parseDouble(valueText);
                if (value < 0 || value > 1) {
                    showAlert("Error", "Value must be between 0 and 1");
                    return;
                }

                Admin.addPromoCode(code, value);
                promoListView.getItems().add(code + " - " + (value * 100) + "% discount");
                newPromoNameField.clear();
                newPromoValueField.clear();
                showAlert("Success", "Promo code added");

            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid number for value");
            }
        });

        removePromoBtn.setOnAction(e -> {
            String code = removePromoField.getText();
            if (code.isEmpty()) {
                showAlert("Error", "Please enter a code to remove");
                return;
            }

            if (Offer.getPromoCodes().containsKey(code)) {
                Admin.removePromoCode(code);
                promoListView.getItems().clear();
                for (Map.Entry<String, Double> entry : Offer.getPromoCodes().entrySet()) {
                    promoListView.getItems().add(entry.getKey() + " - " +
                            (entry.getValue() * 100) + "% discount");
                }
                removePromoField.clear();
                showAlert("Success", "Promo code removed");
            } else {
                showAlert("Error", "Promo code not found");
            }
        });

        promoVBox.getChildren().addAll(
                promoTitle, currentPromosLbl, promoListView,
                addPromoTitle, addPromoGrid,
                removePromoLbl, removePromoBox
        );

        promoTab.setContent(promoVBox);

       
        Tab logoutTab = new Tab("Logout");
        logoutTab.setClosable(false);

        VBox logoutVBox = new VBox(15);
        logoutVBox.setPadding(new Insets(20));
        logoutVBox.setAlignment(Pos.CENTER);

        Label welcomeLbl = new Label("Welcome, Admin " + admin.getName());
        welcomeLbl.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefWidth(150);
        logoutBtn.setOnAction(e -> primaryStage.setScene(createMainMenu()));

        logoutVBox.getChildren().addAll(welcomeLbl, logoutBtn);
        logoutTab.setContent(logoutVBox);

        Tab bookingsTab = new Tab("All Bookings");
        bookingsTab.setClosable(false);

        VBox bookingsVBox = new VBox(15);
        bookingsVBox.setPadding(new Insets(20));

        Label bookingsTitle = new Label("All Bookings");
        bookingsTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        TextArea bookingsTextArea = new TextArea();
        bookingsTextArea.setEditable(false);
        bookingsTextArea.setWrapText(true);
        bookingsTextArea.setPrefHeight(300);

        Button viewBookingsBtn = new Button("View All Bookings");
        viewBookingsBtn.setOnAction(e -> {
            try {
                StringBuilder outputBuilder = new StringBuilder();

                java.util.List<RoomBooking> sortedRooms = new java.util.ArrayList<>(RoomBooking.getRoomBookings());
                java.util.Collections.sort(sortedRooms);
                for (RoomBooking rb : sortedRooms) {
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                    java.io.PrintStream ps = new java.io.PrintStream(baos);
                    java.io.PrintStream old = System.out;
                    System.setOut(ps);
                    rb.printDetails();
                    System.out.flush();
                    System.setOut(old);
                    outputBuilder.append(baos.toString());
                    outputBuilder.append("---------------------------------------\n");
                }

                // Sort and print hall bookings
                java.util.List<HallBooking> sortedHalls = new java.util.ArrayList<>(HallBooking.getHallBookings());
                java.util.Collections.sort(sortedHalls);
                for (HallBooking hb : sortedHalls) {
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                    java.io.PrintStream ps = new java.io.PrintStream(baos);
                    java.io.PrintStream old = System.out;
                    System.setOut(ps);
                    hb.printDetails();
                    System.out.flush();
                    System.setOut(old);
                    outputBuilder.append(baos.toString());
                    outputBuilder.append("---------------------------------------\n");
                }

                bookingsTextArea.setText(outputBuilder.toString());

                if (bookingsTextArea.getText().isEmpty()) {
                    bookingsTextArea.setText("No bookings found.");
                }
            } catch (Exception ex) {
                showAlert("Error", "Failed to load bookings: " + ex.getMessage());
            }
        });

        bookingsVBox.getChildren().addAll(bookingsTitle, viewBookingsBtn, bookingsTextArea);
        bookingsTab.setContent(bookingsVBox);

        // Add all tabs
        tabPane.getTabs().addAll(roomsTab, hallsTab, promoTab, bookingsTab, logoutTab);

        return new Scene(tabPane, 600, 500);
    }

    // === Utility Method for Alerts ===
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // === Main Method ===
    public static void main(String[] args) {
        launch(args);
    }
}
class HallUnavailableException extends Exception {
    public HallUnavailableException(String message) {
        super(message);
    }
} 
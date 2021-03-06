package com.concurrency.CovidTreatment;

public class TreatmentQueue {
    //  Χαρακτηριστικά κλάσης διαθέσιμων κλινών και κρουσμάτων που δεν λαμβάνουν θεραπεία
    private static int incidentCounter = 0;
    private int availableBeds;
    private int unsolvedCases = 0;

    //  Κατασκευαστής
    public TreatmentQueue (int treatmentUnits) {
        availableBeds = treatmentUnits;
    }

    //  Συγχρονισμένη Μέθοδος προσθήκης κρουσμάτων
    public synchronized void newCovidCases(int newCases) {
        if (newCases < 0) {
            throw new IllegalArgumentException("Μη επιτρεπτό μέγεθος");
        } else if (newCases > availableBeds) {  //  Τα κρούσματα υπερβαίνουν τις διαθέσιμες κλίνες
            this.unsolvedCases += newCases - this.availableBeds;    //  Δημιουργούνται κρούσματα χωρίς θεραπεία
            this.availableBeds = 0; //  Αδειάζουν οι κλίνες στο νοσοκομείο
        } else {    //  Οι κλίνες επαρκούν για να δεχθούν τα νέα κρούσματα
            this.availableBeds -= newCases;
        }
        incidentCounter++;
        System.out.format("\nΠεριστατικό: %d, Εμφανίστηκαν %d νέα κρούσματα για νοσηλεία. " +
                "Νέα κρούσματα ΧΩΡΙΣ νοσηλεία: %d. Διαθέσιμες κλίνες: %d",
                incidentCounter, newCases, this.unsolvedCases, this.availableBeds);
    }

    //  Συγχρονισμένη Μέθοδος αφαίρεσης κρουσμάτων
    public synchronized void solveCovidCases(int solvedCases) {
        if (solvedCases < 0) {
            throw new IllegalArgumentException("Μη επιτρεπτό μέγεθος");
        } else if (this.availableBeds + solvedCases >= CovidTreatment.SIZE) {   //  Οι θεραπείες υπερβαίνουν τις συνολικές κλίνες
            solvedCases = CovidTreatment.SIZE - this.availableBeds; //  Ανάρρωση των υπόλοιπων περιστατικών
            this.availableBeds = CovidTreatment.SIZE; //    Διαθεσιμότητα όλων των κλινών
        } else {    //  Οι θεραπείες είναι λιγότερες από το μέγιστο όριο κλινών
            this.availableBeds += solvedCases;
            if (this.unsolvedCases > this.availableBeds) {  //  Τα περιστατικά χωρίς επώαση υπερβαίνουν τις αναρρώσεις
                this.unsolvedCases -= this.availableBeds;   //  Εισαγωγή των περιστατικών χωρίς επώαση
                this.availableBeds = 0; //  Οι κλίνες παραμένουν μηδενικές
            } else {
                this.availableBeds -= this.unsolvedCases;   //  Ικανοποίηση των περιστατικών που δεν έχουν νοσηλευτεί
                this.unsolvedCases = 0; //  Δεν υπάρχουν πλέον κρούσματα χωρίς νοσηλεία
            }
        }
        incidentCounter++;
        System.out.format("\nΠεριστατικό: %d, Ανάρρωσαν %d ασθενείες. " +
                "Νέα κρούσματα ΧΩΡΙΣ νοσηλεία: %d. Διαθέσιμες κλίνες: %d",
                incidentCounter, solvedCases, this.unsolvedCases, this.availableBeds);
    }
}

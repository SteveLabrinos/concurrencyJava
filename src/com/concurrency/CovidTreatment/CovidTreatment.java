package com.concurrency.CovidTreatment;

import java.util.stream.IntStream;

public class CovidTreatment {
    //  Στατικές μεταβλητές προβλήματος
    //  Μέγιστος αριθμός κρουσμάτων k
    public static final int MAX_CASES = 300;
    //  Πλήθος επαναλήψεων
    public static final int LOOPS = 10;
    //  Χωρητικότητα σε εντατικές Ε
    public static final int SIZE = 300;
    //  Μέγιστος αριθμός περίθαλψης h
    public static final int MAX_TREATMENT = 100;
    //  Συχνότητα εμφάνισης κρουσμάτων
    public static final int COVID_FREQUENCY = 1000;
    //  Συχνότητα επώασης κρουσμάτων
    public static final int CURE_FREQUENCY = 5000;
    //  Αριθμός διαθέσιμων νοσοκομείων
    public static final int HOSPITAL_NUM = 3;

    //  Πρόγραμμα εκτέλεσης έναν ιό και μεταβλητό αριθμό διαθέσιμων νοσοκομείων για θεραπεία
    public static void main(String[] args) {
        //  Ορισμός των διαθέσιμων κλινών προς επώαση του ιού
        TreatmentQueue treatmentUnits = new TreatmentQueue(SIZE);
        //  Δημιουργία του ιού
        Covid covid = new Covid(treatmentUnits);
        covid.start();
        //  Δημιουργία των διαθέσιμων νοσοκομείων (1 ή 3 ανάλογα με το ερώτημα)
        Hospital[] hospitals = new Hospital[HOSPITAL_NUM];
        IntStream.range(0, hospitals.length).forEach(i -> {
            hospitals[i] = new Hospital(treatmentUnits);
            hospitals[i].start();
        });
    }
}
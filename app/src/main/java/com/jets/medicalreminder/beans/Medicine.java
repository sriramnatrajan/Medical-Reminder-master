package com.jets.medicalreminder.beans;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jets.medicalreminder.R;

import java.util.Locale;

public class Medicine implements Comparable<Medicine> {


    public enum Type {
        ORAL(R.drawable.oral), TOPICAL(R.drawable.topical), INHALATION(R.drawable.inhalation),
        INJECTION(R.drawable.injection), OTHER(R.drawable.default_medicine);

        private int imageResource;

        Type(int imageResource) {
            this.imageResource = imageResource;
        }

        public int getImageResource() {
            return imageResource;
        }

        public String toString() {
            String origin = super.toString();
            return (origin.substring(0, 1) + origin.substring(1).toLowerCase(Locale.getDefault()));
        }
    }


    public static final int NAME_MAX_LENGTH = 30;
    public static final int DOSE_MAX_VALUE = 10;
    public static final int NOTES_MAX_LENGTH = 255;

    public static final int SORT_BY_NAME = 0;
    public static final int SORT_BY_START_DATE = 1;

    private static int sortType;

    public static int getSortType() {
        return sortType;
    }

    public static void setSortType(int sortType) {
        if (sortType < SORT_BY_NAME || sortType > SORT_BY_START_DATE) {
            return;
        }
        Medicine.sortType = sortType;
    }


    private int id;
    private String name;
    private Type type;
    private int dose;
    private long startDateTime;
    private long endDateTime;
    private long interval;
    private String notes;
    transient private Bitmap image;
    private long startMidTime,startEndTime;
    private long startTime;
    private long midTime;
    private long endTime;
    private   long startDate;

    private int morningHabit;
    private int noonHabit;
    private int eveningHabit;


    public void setMorningIntakeHabit(int morningIntakeHabit) {
        this.morningHabit = morningIntakeHabit;
    }

    public void setNoonIntakeHabit(int noonIntakeHabit) {
        this.noonHabit = noonIntakeHabit;
    }

    public void setEveningIntakeHabit(int eveningIntakeHabit) {
        this.eveningHabit = eveningIntakeHabit;
    }

    public int getMorningIntakeHabit() {
        return morningHabit;
    }

    public int getNoonIntakeHabit() {
        return noonHabit;
    }

    public int getEveningIntakeHabit() {
        return eveningHabit;
    }

    public long getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(long startEndTime) {
        this.startEndTime = startEndTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getMidTime() {
        return midTime;
    }

    public void setMidTime(long midTime) {
        this.midTime = midTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Medicine() {

    }

    public Medicine(int id) {
        setId(id);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }
*/

    public long getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(long startDateTime) {
        this.startDateTime = startDateTime;
    }

    public long getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(long endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof Medicine && id == ((Medicine) object).id;
    }


    @Override
    public int compareTo(@NonNull Medicine medicine) {
        int order = 0;

        if (sortType == SORT_BY_NAME) {
            order = name.compareToIgnoreCase(medicine.name);
            if (order == 0) {
                order = name.compareTo(medicine.name);
            }
        } else if (sortType == SORT_BY_START_DATE) {
            long dateDifference = startDateTime - medicine.getStartDateTime();
            order = dateDifference > 0 ? 1 : -1;
        }

        return order;
    }
}



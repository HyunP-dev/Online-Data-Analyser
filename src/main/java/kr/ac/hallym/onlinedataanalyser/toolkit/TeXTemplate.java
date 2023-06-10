package kr.ac.hallym.onlinedataanalyser.toolkit;

public enum TeXTemplate {
    KNN;

    @Override
    public String toString() {
        switch (this) {
            case KNN -> {
                return "knn-template.tex";
            }
            default -> {
                return super.toString();
            }
        }
    }
}

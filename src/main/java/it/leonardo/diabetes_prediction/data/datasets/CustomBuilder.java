package it.leonardo.diabetes_prediction.data.datasets;

import ai.djl.training.dataset.RandomAccessDataset;

public final class CustomBuilder extends RandomAccessDataset.BaseBuilder<CustomBuilder> {
    
    private double[][] data;
    private double[][] labels;
    
    public CustomBuilder setData(double[][] data) {
        this.data = data;
        return self();
    }

    public CustomBuilder setLabels(double[][] labels) {
        this.data = labels;
        return self();
    }

    @Override
    protected CustomBuilder self() {
        return this;
    }
    
    public CustomDataset build() {
        return new CustomDataset(this);
    }


}

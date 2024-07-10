package it.leonardo.diabetes_prediction.data.datasets;

import ai.djl.training.dataset.RandomAccessDataset;

public final class CustomBuilder extends RandomAccessDataset.BaseBuilder<CustomBuilder> {
    
    double[][] data;
    double[] labels;
    
    public CustomBuilder setData(double[][] data) {
        this.data = data;
        return self();
    }

    public CustomBuilder setLabels(double[] labels) {
        this.labels = labels;
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

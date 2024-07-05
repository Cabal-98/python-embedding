package it.leonardo.diabetes_prediction.data.datasets;


import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.dataset.Record;
import ai.djl.translate.TranslateException;
import ai.djl.util.Progress;

import java.io.IOException;

public class CustomDataset extends RandomAccessDataset {

    private double[][] data;
    private double[][] labels;

    public CustomDataset(CustomBuilder builder) {
        super(builder);
    }

    public static CustomBuilder builder() {
        return new CustomBuilder();
    }

    @Override
    public Record get(NDManager ndManager, long l) throws IOException {

        NDList input = createNDList(ndManager,l);
        NDList output = createNDList(ndManager,l);
        return new Record(input,output);
    }

    private NDList createNDList(NDManager ndManager, long l) {
        NDList lista = new NDList();
        lista.add(ndManager.create(data[(int) l]));
        return lista;
    }

    @Override
    protected long availableSize() {
        return data.length;
    }

    @Override
    public void prepare(Progress progress) throws IOException, TranslateException {

    }
}


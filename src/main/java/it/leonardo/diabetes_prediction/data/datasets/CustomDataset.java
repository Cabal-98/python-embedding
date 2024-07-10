package it.leonardo.diabetes_prediction.data.datasets;


import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.dataset.Record;
import ai.djl.translate.TranslateException;
import ai.djl.util.Progress;
import ai.djl.ndarray.types.DataType;

import java.io.IOException;

public class CustomDataset extends RandomAccessDataset {

    private double[][] data;
    private double[] labels;

    public CustomDataset(CustomBuilder builder) {
        super(builder);
        this.data = builder.data;
        this.labels = builder.labels;
    }

    public static CustomBuilder builder() {
        return new CustomBuilder();
    }

    @Override
    public Record get(NDManager ndManager, long l) throws IOException {
        NDArray inputArray;
        NDArray outputArray;

        NDManager subManager = ndManager.newSubManager();
            inputArray = subManager.create(data[(int) l]).toType(DataType.FLOAT32,true);
            outputArray = subManager.create(labels[(int) l]).toType(DataType.FLOAT32,true);


        NDList input = new NDList(inputArray);
        NDList output = new NDList(outputArray);

        return new Record(input,output);
    }

    private NDList createNDList(NDManager ndManager, double[] array) {
        NDList lista = new NDList();
        NDArray ndArray = ndManager.create(array).toType(DataType.FLOAT32, true);
        lista.add(ndArray);
        return lista;
    }

    @Override
    protected long availableSize() {
        return data.length;
    }

    @Override
    public void prepare(Progress progress) throws IOException, TranslateException {

    }

    public static NDArray toNDArray(double[][] data) {
        NDManager ndManager = NDManager.newBaseManager();
        return ndManager.create(data);
    }

}


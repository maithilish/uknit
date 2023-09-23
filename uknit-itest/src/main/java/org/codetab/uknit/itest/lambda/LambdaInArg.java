package org.codetab.uknit.itest.lambda;

class LambdaInArg {

    public int calculate(final CalcA calcA) {
        calcA.op(1, 2, (a, b) -> a + b);
        @SuppressWarnings("unused")
        int divValue = calcA.op(8, 4, (a, b) -> a / b);
        return calcA.op(6, 3, (a, b) -> a * b);
    }

}

class CalcA {
    public int op(final int a, final int b, final OperationA operationA) {
        return operationA.op(a, b);
    }
}

interface OperationA {
    int op(int a, int b);
}

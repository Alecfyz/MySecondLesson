package model;

import static model.Operations.ADD;
import static model.Operations.DIV;
import static model.Operations.MULT;
import static model.Operations.NON;
import static model.Operations.SUB;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements IntfcCalculator, Parcelable { //
    private float arg1;
    private float arg2;
    private int intPartArg;
    private int fractPartArg;
    private Operations operation = NON;
    private float result;
    //    private String curOperation;
    private boolean afterDOTintering = false;
    private String CurString;


    public Calculator() {
    }

    public Calculator(Parcel in) {
        arg1 = in.readFloat();
        arg2 = in.readFloat();
        intPartArg = in.readInt();
        fractPartArg = in.readInt();
//        afterDOTintering = in.readBoolean();
        result = in.readFloat();
//        operation = in.readTypedObject();
//        CurString = in.readString();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };


    public void readkey(String key) {
//        key = getrealkey(key);
        switch (key) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                addToCurArg(key);
                makeCurString();
                break;
            case "+":
                setOp(ADD);
                makeCurString();

                break;

            case "-":
                setOp(SUB);
                makeCurString();
                break;

            case "*":
                setOp(MULT);
                makeCurString();
                break;

            case "/":
                setOp(DIV);
                makeCurString();
                break;

            case "=":
                prepareToBinaryOperation();
                renew();
                break;

            case ".":
                this.afterDOTintering = true;
                makeCurString();
                break;
        }

    }

    private String getrealkey(String key) {
        if(key.contains("btn")) { // проверка на вшивость
            return key.substring(3);
        }
        return "";
    }

    private void addToCurArg(String key) {
        if (afterDOTintering) {
            addToCurArgAfterDot(key); // дробная часть
        } else { // работаем с целой частью числа
            int curKey = Integer.parseInt(key);
            if (intPartArg == 0) intPartArg = curKey;
            else {
                intPartArg = intPartArg * 10 + curKey;
            }

        }
        Float tmparg = Float.parseFloat(intPartArg + "." + fractPartArg);
        if (operation == NON) { // в данный момент работаем с первым аргументом
            arg1 = tmparg;
        } else { // работаем со вторым аргументом
            arg2 = tmparg;
            prepareToBinaryOperation();
        }
    }

    private void addToCurArgAfterDot(String key) {
        int curKey = Integer.parseInt(key);
        if (fractPartArg == 0) fractPartArg = curKey;
        else {
            fractPartArg = fractPartArg * 10 + curKey;
        }
    }

    private void makeCurString() {
        CurString = "" + arg1;
        if (operation == NON) return;
        CurString += mnemo(operation);
        if (arg2 == 0f) return;
        CurString += arg2;
    }

    private String mnemo(Operations operation) {
        switch (operation) {
            case ADD:
                return "+";

            case SUB:
                return "-";

            case MULT:
                return "*";

            case DIV:
                return "/";
        }
        return "NONE";
    }

    private void clearCurString() {
        this.CurString = "";
//        CurString = "";
    }

    public String getCurString() {
        return this.CurString;
    }

    private void setOp(Operations oper) {
//
        operation = oper;
        intPartArg = 0;
        fractPartArg = 0;
        afterDOTintering = false;

        // если операция вызывется сразу после другой операции
        if (result != 0f) {
            arg1 = result;
            arg2 = 0f;
            result = 0f;
        }
    }

    //private void convertToArg(String argstr){
    //float arg;
    //if(!CurString.equals("")){
    //arg = Float.parseFloat(CurString);
    //} else {
    //arg = 0f;
    //}
    //if (argstr.equals("arg1")) this.arg1 = arg; else this.arg2 = arg;
    //}

    public void prepareToBinaryOperation() {
        // проверяем наличчие обоих аргументов и текущей операции. Если все ок, выполняем бинарную операцию
        if (arg1 != 0f && arg2 != 0f && operation != NON) {
            doBinaryOperation(arg1, arg2, operation);
        } else {
        }
    }

/*
    public static Resources getResourses() {
        return src;
    }*/


    public void doBinaryOperation(float arg1, float arg2, Operations op) {
        clear(arg1, arg2);

        Context context;
//        String message = context.getString(R.string.asd);
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        switch (op) {
            case ADD:
                doAddition();
                break;
            case DIV:
                doDivision(this.arg1, this.arg2);
                break;
            case SUB:
                doSubtraction(this.arg1, this.arg2);
                break;
            case MULT:
                doMultiplication(this.arg1, this.arg2);
                break;
            default:
//                throw new IllegalStateException(R.string.stext + op);
                throw new IllegalStateException("Illegal operation " + op);
        }
    }

    private void doAddition() {
        this.result = this.arg1 + this.arg2;
    }

    private void doDivision(float arg1, float arg2) {
        this.result = arg1 / arg2;
    }

    private void doSubtraction(float arg1, float arg2) {
        this.result = arg1 - arg2;
    }

    private void doMultiplication(float arg1, float arg2) {
        this.result = arg1 * arg2;
    }

    private void clear(float arg1, float arg2) {
        // do clear values and put it
        this.arg1 = (float) arg1;
        this.arg2 = (float) arg2;
    }

    public float getResult() {
        this.CurString = String.valueOf(this.result);
        return this.result;
    }

    public float getArg1() {
        return arg1;
    }

    public float getArg2() {
        return arg2;
    }

    public String getCurOperation() {
        if (operation != null) return operation.toString();
        else return "nope";
    }

    private void renew() {
        arg1 = 0f;
        arg2 = 0f;
        intPartArg = 0;
        fractPartArg = 0;
        operation = NON;
        CurString = "";
        afterDOTintering = false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(arg1);
        dest.writeFloat(arg2);
        dest.writeInt(intPartArg);
        dest.writeInt(fractPartArg);
//        dest.writeBoolean(afterDOTintering);
        dest.writeFloat(result);
//        dest.writeString(CurString);
    }


}

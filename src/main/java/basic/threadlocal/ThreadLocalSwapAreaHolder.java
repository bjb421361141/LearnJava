package basic.threadlocal;

import basic.threadlocal.inf.SwapArea;

/**
 * 线程变量保存
 */
public class ThreadLocalSwapAreaHolder {
    ThreadLocal<SwapArea> holder = new ThreadLocal<SwapArea>();

    void setCurrentSwapArea(SwapArea swapArea) {
        this.holder.set(swapArea);
    }

    SwapArea getCurrentSwapArea() {
        return this.holder.get();
    }

    SwapArea removeCurrentSwapArea() {
        SwapArea tmp = this.getCurrentSwapArea();
        this.holder.remove();
        return tmp;
    }


}

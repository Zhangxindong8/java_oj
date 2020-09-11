package compile;

public class Answer {
    //表示当前的错误类型，1表示编译出错 2表示运行出错
    private int error;
    //出错原因 编译出错，运行出错（异常信息）
    private String reason;
    //标准输出的内容
    private String stdout;
    //标准错误的内容
    private String stderr;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }
}

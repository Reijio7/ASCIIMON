public class IntroController {

    private volatile boolean skip = false;

    public void skip() {
        skip = true;
    }

    public boolean isSkipped() {
        return skip;
    }
}
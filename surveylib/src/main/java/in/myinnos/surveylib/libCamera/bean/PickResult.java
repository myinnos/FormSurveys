package in.myinnos.surveylib.libCamera.bean;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by jrvansuita build 02/12/16.
 */

public class PickResult {
    private Bitmap bitmap;
    private Uri uri;
    private String path;
    private Throwable error;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Uri getUri() {
        return uri;
    }

    public PickResult setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

    public Throwable getError() {
        return error;
    }

    public PickResult setError(Exception error) {
        this.error = error;
        return this;
    }

    public PickResult setError(Throwable error) {
        this.error = error;
        return this;
    }

    public PickResult setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPath() {
        return path;
    }
}

package androidx.emoji2.viewsintegration;

import android.os.Build;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public final class EmojiEditTextHelper {
    private final HelperInternal mHelper;

    public EmojiEditTextHelper(EditText editText, boolean z) {
        Preconditions.checkNotNull(editText, "editText cannot be null");
        if (Build.VERSION.SDK_INT < 19) {
            this.mHelper = new HelperInternal();
        } else {
            this.mHelper = new HelperInternal19(editText, z);
        }
    }

    public KeyListener getKeyListener(KeyListener keyListener) {
        return this.mHelper.getKeyListener(keyListener);
    }

    public InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo) {
        if (inputConnection == null) {
            return null;
        }
        return this.mHelper.onCreateInputConnection(inputConnection, editorInfo);
    }

    public void setEnabled(boolean z) {
        this.mHelper.setEnabled(z);
    }

    static class HelperInternal {
        KeyListener getKeyListener(KeyListener keyListener) {
            return keyListener;
        }

        InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo) {
            return inputConnection;
        }

        void setEnabled(boolean z) {
        }

        HelperInternal() {
        }
    }

    private static class HelperInternal19 extends HelperInternal {
        private final EditText mEditText;
        private final EmojiTextWatcher mTextWatcher;

        HelperInternal19(EditText editText, boolean z) {
            this.mEditText = editText;
            EmojiTextWatcher emojiTextWatcher = new EmojiTextWatcher(editText, z);
            this.mTextWatcher = emojiTextWatcher;
            editText.addTextChangedListener(emojiTextWatcher);
            editText.setEditableFactory(EmojiEditableFactory.getInstance());
        }

        @Override // androidx.emoji2.viewsintegration.EmojiEditTextHelper.HelperInternal
        KeyListener getKeyListener(KeyListener keyListener) {
            if (keyListener instanceof EmojiKeyListener) {
                return keyListener;
            }
            if (keyListener == null) {
                return null;
            }
            return keyListener instanceof NumberKeyListener ? keyListener : new EmojiKeyListener(keyListener);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiEditTextHelper.HelperInternal
        InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo) {
            return inputConnection instanceof EmojiInputConnection ? inputConnection : new EmojiInputConnection(this.mEditText, inputConnection, editorInfo);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiEditTextHelper.HelperInternal
        void setEnabled(boolean z) {
            this.mTextWatcher.setEnabled(z);
        }
    }
}

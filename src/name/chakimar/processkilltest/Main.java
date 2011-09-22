package name.chakimar.processkilltest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import name.chakimar.processkilltest.R;

public class Main extends Activity {
	
	private static final int DIALOG_ID = 0;
	private boolean killProcess;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		switch (event.getAction()) {
		case KeyEvent.ACTION_DOWN:
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_BACK:
				showDialog(DIALOG_ID);
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID:
			AlertDialog dialog = new AlertDialog.Builder(this)
			.setPositiveButton("プロセスも終了させる", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Main.this.killProcess = true;
					finish();
					/*
					 * プロセス終了はonDestoryの最後に行う。
					 * ここでプロセス終了をしてしまうと、onDestroyの処理が途中で終わってしまうことがある。
					 */
					//Process.killProcess(Process.myPid());
				}
			})
			.setNegativeButton("プロセスは終了しない", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Main.this.killProcess = false;
					finish();
				}
			})
			
			.create();
			return dialog;
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (killProcess) Process.killProcess(Process.myPid());
	}
    
	
    
}
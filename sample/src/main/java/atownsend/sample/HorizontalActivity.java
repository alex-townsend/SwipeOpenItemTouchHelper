package atownsend.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;


public class HorizontalActivity extends AppCompatActivity implements TestAdapter.ButtonCallbacks {

  private TestAdapter adapter;

  public static void start(Context context) {
      Intent starter = new Intent(context, HorizontalActivity.class);
      context.startActivity(starter);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_horizontal);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final RecyclerView recyclerView = (RecyclerView) findViewById(atownsend.sample.R.id.recycler_view);
    adapter = new TestAdapter(this, true, this);
    SwipeOpenItemTouchHelper helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
        SwipeOpenItemTouchHelper.UP | SwipeOpenItemTouchHelper.DOWN));

    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    recyclerView.setAdapter(adapter);
    helper.attachToRecyclerView(recyclerView);
  }

  @Override public void removePosition(int position) {
    adapter.removePosition(position);
  }

  @Override public void editPosition(int position) {
    Toast.makeText(HorizontalActivity.this, "Edit position: " + position, Toast.LENGTH_SHORT).show();
  }
}

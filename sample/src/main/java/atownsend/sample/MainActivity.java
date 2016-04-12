package atownsend.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;

public class MainActivity extends AppCompatActivity implements TestAdapter.ButtonCallbacks {

  private TestAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    adapter = new TestAdapter(this, false, this);
    SwipeOpenItemTouchHelper helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
        SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    helper.attachToRecyclerView(recyclerView);

  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_switch) {
      HorizontalActivity.start(this);
    }
    return true;
  }

  @Override public void removePosition(int position) {
    adapter.removePosition(position);
  }

  @Override public void editPosition(int position) {
    Toast.makeText(MainActivity.this, "Edit position: " + position, Toast.LENGTH_SHORT).show();
  }
}

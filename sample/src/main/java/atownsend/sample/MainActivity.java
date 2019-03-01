package atownsend.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;

/**
 * Example activity that displays the use of state saving/restoration, as well as disabling closeOnAction
 */
public class MainActivity extends AppCompatActivity implements TestAdapter.ButtonCallbacks {

  private TestAdapter adapter;
  private SwipeOpenItemTouchHelper helper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final RecyclerView recyclerView = findViewById(R.id.recycler_view);
    adapter = new TestAdapter(this, false, this);
    helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
        SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    helper.attachToRecyclerView(recyclerView);
    helper.setCloseOnAction(false);

    if (savedInstanceState != null) {
      helper.restoreInstanceState(savedInstanceState);
    }
  }

  @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    helper.onSaveInstanceState(outState);
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

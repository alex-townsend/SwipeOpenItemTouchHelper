package atownsend.swipeopenhelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import atownsend.swipeopenhelper.test.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Test Activity for instrumentation tests with a SwipeOpenItemTouchHelper
 */
public class SwipeOpenItemTouchHelperTestActivity extends Activity {

  SwipeOpenItemTouchHelper helper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    RecyclerView recyclerView = new RecyclerView(this);
    recyclerView.setId(R.id.test_recycler);
    recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new TestAdapter(this));
    setContentView(recyclerView);

    helper = new SwipeOpenItemTouchHelper(
        new SwipeOpenItemTouchHelper.SimpleCallback(
            SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));
    helper.attachToRecyclerView(recyclerView);

    if (savedInstanceState != null) {
      helper.restoreInstanceState(savedInstanceState);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    helper.onSaveInstanceState(outState);
  }

  private static final class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<String> items = new ArrayList<>();

    public TestAdapter(Context context) {
      this.context = context;
      items = generateTestData();
    }

    private List<String> generateTestData() {
      List<String> testData = new ArrayList<>();

      testData.add("Test 0");
      testData.add("Test 1");
      testData.add("Test 2");
      testData.add("Test 3");
      testData.add("Test 4");
      testData.add("Test 5");
      testData.add("Test 0");
      testData.add("Test 1");
      testData.add("Test 2");
      testData.add("Test 3");
      testData.add("Test 4");
      testData.add("Test 5");
      testData.add("Test 0");
      testData.add("Test 1");
      testData.add("Test 2");
      testData.add("Test 3");
      testData.add("Test 4");
      testData.add("Test 5");
      testData.add("Test 0");
      testData.add("Test 1");
      testData.add("Test 2");
      testData.add("Test 3");
      testData.add("Test 4");
      testData.add("Test 5");
      testData.add("Test 0");
      testData.add("Test 1");
      testData.add("Test 2");
      testData.add("Test 3");
      testData.add("Test 4");
      testData.add("Test 5");
      testData.add("Test 0");
      testData.add("Test 1");
      testData.add("Test 2");
      testData.add("Test 3");
      testData.add("Test 4");
      testData.add("Test 5");

      return testData;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new TestViewHolder(
          LayoutInflater.from(context).inflate(R.layout.test_view_holder, parent, false));
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      ((TestViewHolder) holder).textView.setText(items.get(position));
    }

    @Override public int getItemCount() {
      return items.size();
    }

    static class TestViewHolder extends BaseSwipeOpenViewHolder {

      public LinearLayout contentView;
      public TextView textView;
      public TextView deleteButton;
      public TextView editButton;

      public TestViewHolder(final View view) {
        super(view);
        contentView = (LinearLayout) view.findViewById(R.id.content_view);
        textView = (TextView) view.findViewById(R.id.display_text);
        deleteButton = (TextView) view.findViewById(R.id.delete_button);
        editButton = (TextView) view.findViewById(R.id.edit_button);
      }

      @NonNull @Override public View getSwipeView() {
        return contentView;
      }

      @Override public float getEndHiddenViewSize() {
        return editButton.getMeasuredWidth();
      }

      @Override public float getStartHiddenViewSize() {
        return deleteButton.getMeasuredWidth();
      }
    }
  }
}

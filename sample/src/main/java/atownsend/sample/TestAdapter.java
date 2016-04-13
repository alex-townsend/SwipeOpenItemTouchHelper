package atownsend.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;
import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private final boolean horizontal;
  private final ButtonCallbacks callbacks;
  private List<String> items = new ArrayList<>();

  interface ButtonCallbacks {
    void removePosition(int position);
    void editPosition(int position);
  }

  public TestAdapter(Context context, boolean horizontal, ButtonCallbacks callbacks) {
    this.context = context;
    this.horizontal = horizontal;
    this.callbacks = callbacks;
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
    if (horizontal) {
      return new HorizontalTestViewHolder(
          LayoutInflater.from(context).inflate(R.layout.view_holder_horizontal_view, parent, false), callbacks);
    } else {
      return new TestViewHolder(
          LayoutInflater.from(context).inflate(R.layout.view_holder_view, parent, false), callbacks);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (horizontal) {
      ((HorizontalTestViewHolder) holder).textView.setText(items.get(position));
    } else {
      ((TestViewHolder) holder).textView.setText(items.get(position));
    }
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void removePosition(int adapterPosition) {
    items.remove(adapterPosition);
    notifyItemRemoved(adapterPosition);
  }

  static class TestViewHolder extends BaseSwipeOpenViewHolder {

    public LinearLayout contentView;
    public TextView textView;
    public TextView deleteButton;
    public TextView editButton;

    public TestViewHolder(final View view, final ButtonCallbacks callbacks) {
      super(view);
      contentView = (LinearLayout) view.findViewById(R.id.content_view);
      textView = (TextView) view.findViewById(R.id.display_text);
      deleteButton = (TextView) view.findViewById(R.id.delete_button);
      editButton = (TextView) view.findViewById(R.id.edit_button);

      deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          callbacks.removePosition(getAdapterPosition());
        }
      });

      editButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          callbacks.editPosition(getAdapterPosition());
        }
      });
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

    @Override public void notifyStartOpen() {
      itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.red));
    }

    @Override public void notifyEndOpen() {
      itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.blue));
    }
  }

  static class HorizontalTestViewHolder extends BaseSwipeOpenViewHolder {
    public LinearLayout contentView;
    public TextView textView;
    public TextView deleteButton;
    public TextView editButton;

    public HorizontalTestViewHolder(final View view, final ButtonCallbacks callbacks) {
      super(view);
      contentView = (LinearLayout) view.findViewById(R.id.content_view);
      textView = (TextView) view.findViewById(R.id.display_text);
      deleteButton = (TextView) view.findViewById(R.id.delete_button);
      editButton = (TextView) view.findViewById(R.id.edit_button);

      deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          callbacks.removePosition(getAdapterPosition());
        }
      });

      editButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          callbacks.editPosition(getAdapterPosition());
        }
      });
    }

    @NonNull @Override public View getSwipeView() {
      return contentView;
    }

    @Override public float getEndHiddenViewSize() {
      return editButton.getMeasuredHeight();
    }

    @Override public float getStartHiddenViewSize() {
      return deleteButton.getMeasuredHeight();
    }
  }
}

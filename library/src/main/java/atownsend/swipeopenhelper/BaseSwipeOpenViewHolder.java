package atownsend.swipeopenhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A Base class for a SwipeView Holder that implements the {@link SwipeOpenViewHolder} interface.
 * Users can extend this base class when implementing the swipe-to-open functionality
 */
public abstract class BaseSwipeOpenViewHolder extends RecyclerView.ViewHolder
    implements SwipeOpenViewHolder {

  public BaseSwipeOpenViewHolder(View itemView) {
    super(itemView);
  }

  @NonNull @Override public RecyclerView.ViewHolder getViewHolder() {
    return this;
  }

  @Override public void notifyStartOpen() {
    // no-op
  }

  @Override public void notifyEndOpen() {
    // no-op
  }
}

package kalah.board;

import java.util.ArrayList;

public class CircularArrayList<T> extends ArrayList<T> {
	
	/* (non-Javadoc)
	 * If the index exceeds the array size, goes around to the beginning
	 * @see java.util.ArrayList#get(int)
	 */
	@Override
	public T get(int index) {
		return super.get(index%size());
    }
	
}

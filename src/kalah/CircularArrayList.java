package kalah;

import java.util.ArrayList;

public class CircularArrayList<T> extends ArrayList<T> {
	
	@Override
	public T get(int index) {
		return super.get(index%size());
    }
	
}

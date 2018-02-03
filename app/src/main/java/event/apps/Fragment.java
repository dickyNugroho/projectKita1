package event.apps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rejak on 3/4/2017.
 */

public class Fragment extends android.app.Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_default,container, false);
        View tv = v.findViewById(R.id.fragment);
        ((TextView)tv).setText("");
        return v;
    }
}

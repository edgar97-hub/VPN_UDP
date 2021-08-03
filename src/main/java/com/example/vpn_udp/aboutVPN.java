package com.example.vpn_udp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class aboutVPN  extends Fragment {

    private View view;

    public aboutVPN() {
        // Required empty public constructor
    }

    private void renderWebView() {
        String YourHtmlPage = "<html>\n" +
                "  <head>\n" +
                "    <title>VPNTree: About App</title>\n" +
                "  </head> \n" +
                "  <body style=\"margin:10px;\">\n" +
                "<h3 style=\"text-align: center;\"><span style=\"color: #999999;\">VPN udp.</span></h3>\n" +
                "<h3 style=\"text-align: center;\"><span style=\"color: #999999;\">Tu IP publica sera cambiada por la servidor vpn.</span></h3>\n" +
                "<h3 style=\"text-align: center;\"><span style=\"color: #999999;\">Esta aplicacion esta proceso de implementacion.</span></h3>\n" +
                "<h4>&nbsp;</h4>\n" +
                "<h4><span style=\"color: #999999;\">caracteristicas:</span></h4>\n" +
                "<h4><span style=\"color: #999999;\">no permite login</span></h4>\n" +
                "<h4><span style=\"color: #999999;\">no posee encriptacion</span></h4>\n" +
                "<h4>&nbsp;</h4>\n" +
                "<h2><span style=\"text-decoration: underline;\"><span style=\"color: #999999; text-decoration: underline;\">VPN para android.</span></span></h2>\n" +
                " </body>\n" +
                "  </html>";
        WebView Data = (WebView) view.findViewById(R.id.about_app_text);
        Data.loadDataWithBaseURL(null, YourHtmlPage, "text/html", "UTF-8", null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_app, container, false);
        renderWebView();
        return view;
    }
}

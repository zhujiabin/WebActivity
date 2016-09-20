package com.ihongqiqu.webactivity;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragment extends Fragment {
    private static final String ARG_PARAM_URL = "url";

    private String mUrl;
    private WebView mWebView;

    private OnFragmentInteractionListener mListener;

    public WebFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url url 链接
     * @return A new instance of fragment WebFragment.
     */
    public static WebFragment newInstance(String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM_URL);
        }
    }

    private void initWebView() {
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new HTMLheaderJavaScriptInterface(), "local_obj");
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
        mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示
//        mWebView.setWebViewClient(new MyWebViewClient());
//        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.getSettings().setUseWideViewPort(true);
        // 安全考虑，防止密码泄漏，尤其是root过的手机
        mWebView.getSettings().setSavePassword(false);
        String ua = mWebView.getSettings().getUserAgentString();
        String appUA = ua + "; MYAPP";
        mWebView.getSettings().setUserAgentString(appUA);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


        mWebView.getSettings().setDatabaseEnabled(true);
        String dir = getActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();

        //启用地理定位
        mWebView.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        mWebView.getSettings().setGeolocationDatabasePath(dir);

        //最重要的方法，一定要设置，这就是出不来的主要原因
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.loadUrl(mUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        mWebView = (WebView) view.findViewById(R.id.wv_content);
        initWebView();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

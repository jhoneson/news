使用方法：
第一步:在xml中指定布局：

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
    <com.scxh.slider.library.SliderLayout
        android:id="@+id/read_news_slider"
        android:layout_width="match_parent"
        android:layout_height="180dp" />
    <com.scxh.slider.library.Indicators.PagerIndicator
        android:id="@+id/read_news_indicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</RelativeLayout>

第二步 代码中使用:

 mSliderLayout = (SliderLayout) sliderHeaderView.findViewById(R.id.read_news_slider);

 public void showReadPic(List<NewsTextBean> readList) {
    if (mCurrentPage == 0) {
        mSliderLayout.removeAllSliders();// 移除所有的Slider
        int length = readList.size() > 3 ? 3 : readList.size();
        for (int i = 0; i <= length; i++) {
            TextSliderView textSlider = new TextSliderView(getActivity());
            textSlider.description(readList.get(i).getTitle())
                    .image(readList.get(i).getShowPic())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            final String docId = readList.get(i).getDocId();
            textSlider.setOnSliderClickListener(new OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    NewsContentActivity.onStartActivityAction(getActivity(), docId);
                }
            });// 实现TextSliderView的点击监听
            mSliderLayout.addSlider(textSlider); // 添加进SliderLayout
        }
        // 设置指示器位置
        mSliderLayout.setPresetIndicator(PresetIndicators.Right_Bottom);
        // 指示符是否显示
        mSliderLayout.setIndicatorVisibility(IndicatorVisibility.Visible);
    }
 }

第三步销毁

@Override
 public void onStop() {
     super.onStop();
     if (mSliderLayout != null) {
         mSliderLayout.stopAutoCycle();
     }
 }
package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.Focus_date;


/**
 * Created by Administrator on 8/24/2017.
 */

public class HistoryFocusAdapter extends BaseAdapter {
    private Context context;
    private List<Focus_date> list;
    LayoutInflater lay;

    public HistoryFocusAdapter(Context context, List<Focus_date> list) {
        this.context = context;
        this.list = list;
        lay = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View itemView = lay.inflate(R.layout.item_history_focus, null);
        LinearLayout lay_display;
        TextView tvSetupTime, tvDate, tvName, tvTarget, tvStatus;
        ImageView imageOrder, imageMeeting, imageCall, imageEmail, imageEvent;

        lay_display = (LinearLayout) itemView.findViewById(R.id.lay_display);
        tvSetupTime = (TextView) itemView.findViewById(R.id.tvSetupTime);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        tvTarget = (TextView) itemView.findViewById(R.id.tvTarget);
        imageOrder = (ImageView) itemView.findViewById(R.id.imageOrder);
        imageMeeting = (ImageView) itemView.findViewById(R.id.imageMeeting);
        imageCall = (ImageView) itemView.findViewById(R.id.imageCall);
        imageEmail = (ImageView) itemView.findViewById(R.id.imageEmail);
        imageEvent = (ImageView) itemView.findViewById(R.id.imageEvent);
        if (position % 2 != 0)
            lay_display.setBackgroundResource(R.color.colorWhite);
        else  lay_display.setBackgroundResource(R.color.color1);
        Focus_date focus = list.get(position);
//        if (focus.getFocusTypeId() == 2)
//            holder.tvSetupTime.setText(focus.getFocusTypeName());
//        else
       tvSetupTime.setText(context.getResources().getString(R.string.srtTo) + " " + convertStringToDate(focus.getBeginDate()) + " : " +focus.getNumberDate()+ " " + context.getResources().getString(R.string.srtDate));


        //if(focus.getNumberDate()>0)
       tvDate.setText(funcNumberDate(convertStringToDate(focus.getModify_date()), convertStringToDate(focus.getBeginDate()))+"" + " " + context.getResources().getString(R.string.srtDate));
//        else  holder.tvDate.setText(context.getResources().getString(R.string.srtToDay));
      tvName.setText(focus.getClientName());
       tvTarget.setText(focus.getFocusTargetName());
        tvStatus.setText(focus.getFocusStatusName());
        if(focus.getNumberOrder()>0){
            imageOrder.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberMeeting()>0){
          imageMeeting.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberCall()>0){
           imageCall.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberEmail()>0){
           imageEmail.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberEvent()>0){
            imageEvent.setVisibility(View.VISIBLE);
        }
        return itemView;
    }
    public String convertStringToDate(String stringData)
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");//yyyy-MM-dd'T'HH:mm:ss
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = sdf.parse(stringData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(data);
        return formattedTime;
    }
    public boolean isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "dd/MM/yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }
    public int funcNumberDate(String Modify, String enDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        int date = 0;
        try {
            Date date1 = null, date2 = null;
            try {
                date1 = myFormat.parse(Modify);
                date2 = myFormat.parse(enDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            boolean check = isDateAfter(Modify, enDate);
            //ngày Modify lớn hơn ngày beginDate
            if (check == false) {
                long diff = date1.getTime() - date2.getTime();
                date = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            }
            //ngày Modify nhỏ hơn ngày beginDate
            else {
                long diff = date2.getTime() - date1.getTime();
                date = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

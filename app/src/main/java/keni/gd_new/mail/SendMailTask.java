package keni.gd_new.mail;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Keni on 14.03.2016.
 */
public class SendMailTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object... args)
    {
        try
        {
            Log.i("SendMailTask", "Инициализация....");
            publishProgress("Обработка....");
            GMail androidEmail = new GMail(args[0].toString());
            publishProgress("Подготовка сообщения....");
            androidEmail.createEmailMessage();
            publishProgress("Отправка....");
            androidEmail.sendEmail();
            publishProgress("Письмо отправлено....");
        }
        catch (Exception e)
        {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }
}

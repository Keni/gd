package keni.gd_new.mail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import keni.gd_new.R;

/**
 * Created by Keni on 14.03.2016.
 */
public class MailSendDialog extends DialogFragment
{
    private String id, title, phone;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogview = inflater.inflate(R.layout.send_mail, null);
        builder.setView(dialogview);

        final EditText phone = (EditText) dialogview.findViewById(R.id.editTextPhone);
        final TextView error = (TextView) dialogview.findViewById(R.id.textViewError);


        builder.setPositiveButton(R.string.send, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (phone.length() > 5)
                        {
                            id = getArguments().getString("id");
                            title = getArguments().getString("title");
                            new SendMailTask().execute("Бронирование квартиры " + id + " по адресу " + title + "\n" + "Номер телефона: " + phone.getText() + "\n" + "Отправлено с Android");
                            Toast.makeText(getActivity().getApplicationContext(), "Ваша заявка отправлена.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            setCancelable(false);
                            error.setText("Ошибка!");

                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        MailSendDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }


}

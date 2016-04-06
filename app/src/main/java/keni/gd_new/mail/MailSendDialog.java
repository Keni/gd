package keni.gd_new.mail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import keni.gd_new.R;

/**
 * Created by Keni on 14.03.2016.
 */
public class MailSendDialog extends DialogFragment
{
    private String id, title;
    private EditText phone;
    private TextView error;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogview = inflater.inflate(R.layout.send_mail, null);
        builder.setView(dialogview);

        phone = (EditText) dialogview.findViewById(R.id.editTextPhone);
        phone.setSelection(2);
        error = (TextView) dialogview.findViewById(R.id.textViewError);


        // Отмена закрытия окна при не выполнение условия
        builder.setPositiveButton(R.string.send, null);
        // Закрытие окна при нажатие кнопки
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MailSendDialog.this.getDialog().cancel();
            }
        });
        return builder.create();

    }

    @Override
    public void onStart()
    {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();

        if (dialog != null)
        {
            Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

            positive.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Проверка, что введеный номер телефона больше 7 значений
                    if (phone.length() >= 7)
                    {
                        // Если больше 7, формирования письма и отправка на почту
                        id = getArguments().getString("id");
                        title = getArguments().getString("title");
                        new SendMailTask().execute("Бронирование квартиры " + id + " по адресу " + title + "\n" + "Номер телефона: " + phone.getText() + "\n" + "Отправлено с Android");
                        MailSendDialog.this.getDialog().cancel();
                        Toast.makeText(getActivity().getApplicationContext(), "Ваша заявка отправлена.", Toast.LENGTH_LONG).show();
                    }
                    // Если меньше, сообщение об ошибке
                    else
                    {
                        error.setText("Ошибка! Проверьте правильность набранного номера!");
                    }
                }
            });
        }
    }


}

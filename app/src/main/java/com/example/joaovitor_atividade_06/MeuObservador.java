package com.example.joaovitor_atividade_06;

import android.app.NotificationManager;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import androidx.core.app.NotificationCompat;

public class MeuObservador extends ContentObserver
{
    private Context context;

    public MeuObservador(Handler handler, Context context)
    {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Atualização de Dados")
                .setContentText("Novos dados foram inseridos")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }
}

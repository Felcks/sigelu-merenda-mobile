package com.sigelu.merenda.ui.lista_pedidos;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.sigelu.merenda.R;
import com.sigelu.merenda.common.domain.model.accounts.DataHolder;
import com.sigelu.utils.menu_lateral.Preparador;
import com.sigelu.utils.menu_lateral.PrepararMenuLateral;

class PreparadorConcreto implements Preparador {

    private final ListaPedidoActivity activity;
    private final DataHolder dataHolder;

    PreparadorConcreto(ListaPedidoActivity mapaActivity) {
        this.activity = mapaActivity;
        this.dataHolder = DataHolder.INSTANCE;
    }

    @Override
    public String getNomeUsuario() {
        return this.dataHolder.getNomeUsuario();
    }

    @Override
    public Toolbar prepararToolbar() {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public DrawerLayout prepararDrawer() {
        return activity.findViewById(R.id.drawer_layout);
    }

    @Override
    public NavigationView prepararNavegador() {
        NavigationView navegador = activity.findViewById(R.id.nav_view);
        View v =navegador.getHeaderView(0);
        final TextView textView = v.findViewById(R.id.nome_usuario);


//        new FotoMenuLateralTask(new Carregavel() {
//            @Override
//            public void acabou(Bitmap bitmap) {
//                if (bitmap != null) {
//                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(), ImageUtil.scaleDown(bitmap, 100, true));
//                    roundedBitmapDrawable.setCircular(true);
//                    roundedBitmapDrawable.setAntiAlias(true);
//                    textView.setCompoundDrawablesWithIntrinsicBounds(roundedBitmapDrawable, null, null, null);
//                }
//            }
//        }).execute(dataHolder.getFotoUsuario());

        return navegador;

    }

    @Override
    public Activity getActivity() {
        return this.activity;
    }

    @Override
    public void removerMenus(Menu menu) {}

    @Override
    public void trocarModulo(Integer moduloId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataHolder.getSchemeLauncher()));
        intent.putExtra("moduloId", moduloId);
        intent.setAction(Intent.ACTION_VIEW);

        ((NotificationManager) activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        activity.startActivity(intent);
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_sobre:
                PrepararMenuLateral.irParaSobre(this.activity);
                break;
            case R.id.nav_chat:
                PrepararMenuLateral.irParaChatZendesk(activity, dataHolder.getNomeUsuario(), dataHolder.getEmailUsuario(), activity.getString(R.string.app_name));
                break;
            case R.id.nav_voltar_menu:
                this.activity.onBackPressed();
                break;
        }
        return false;
    }

}

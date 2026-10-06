package com.agrilink.app;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int GREEN = Color.rgb(11, 93, 53);
    private static final int LIGHT_GREEN = Color.rgb(110, 170, 24);
    private static final int BG = Color.rgb(247, 250, 245);
    private static final int TEXT = Color.rgb(24, 49, 38);
    private static final int MUTED = Color.rgb(102, 117, 109);
    private static final int GOLD = Color.rgb(217, 164, 65);

    private LinearLayout root;
    private LinearLayout content;
    private TextView title;
    private final List<Product> products = new ArrayList<>();
    private final List<Product> cart = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(GREEN);
        getWindow().setNavigationBarColor(BG);
        seedProducts();
        showApp();
    }

    private void seedProducts() {
        products.add(new Product("Fresh Bananas", "Kampala", "UGX 8,000 / bunch", "🍌"));
        products.add(new Product("Sweet Maize", "Wakiso", "UGX 2,000 / cob", "🌽"));
        products.add(new Product("Fresh Tomatoes", "Mukono", "UGX 6,000 / kg", "🍅"));
        products.add(new Product("Green Vegetables", "Entebbe", "UGX 4,000 / bundle", "🥬"));
        products.add(new Product("Fresh Eggs", "Gayaza", "UGX 15,000 / tray", "🥚"));
        products.add(new Product("Cassava", "Luweero", "UGX 2,500 / kg", "🌱"));
    }

    private void showApp() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        setContentView(root);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(dp(16), dp(10), dp(16), dp(6));
        top.setBackgroundColor(Color.WHITE);

        ImageView logo = new ImageView(this);
        logo.setImageResource(com.agrilink.app.R.drawable.agrilink_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        top.addView(logo, new LinearLayout.LayoutParams(dp(48), dp(48)));

        title = text("Agrilink", 22, TEXT, Typeface.BOLD);
        top.addView(title, new LinearLayout.LayoutParams(0, dp(48), 1));

        TextView cartIcon = text("🛒", 25, GREEN, Typeface.NORMAL);
        cartIcon.setGravity(Gravity.CENTER);
        cartIcon.setOnClickListener(v -> showTab(3));
        top.addView(cartIcon, new LinearLayout.LayoutParams(dp(52), dp(48)));
        root.addView(top);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        root.addView(bottomNav());
        showTab(0);
    }

    private LinearLayout bottomNav() {
        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(4), dp(6), dp(4), dp(6));
        nav.setBackgroundColor(Color.WHITE);
        String[] labels = {"⌂\nHome", "🛒\nShop", "+\nSell", "🛍\nCart", "👤\nProfile"};
        for (int i = 0; i < labels.length; i++) {
            final int tab = i;
            TextView b = text(labels[i], 12, MUTED, Typeface.BOLD);
            b.setGravity(Gravity.CENTER);
            b.setPadding(2, 4, 2, 4);
            b.setOnClickListener(v -> showTab(tab));
            nav.addView(b, new LinearLayout.LayoutParams(0, dp(58), 1));
        }
        return nav;
    }

    private void showTab(int tab) {
        currentTab = tab;
        content.removeAllViews();
        switch (tab) {
            case 0: home(); break;
            case 1: marketplace(); break;
            case 2: sell(); break;
            case 3: cart(); break;
            case 4: profile(); break;
            default: home();
        }
    }

    private void home() {
        LinearLayout page = page();

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.agrilink_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        page.addView(logo, new LinearLayout.LayoutParams(-1, dp(190)));

        TextView hero = text("Smart Farming\nSustainable Future\nFood Security for All", 28, TEXT, Typeface.BOLD);
        hero.setPadding(dp(4), dp(4), dp(4), dp(10));
        page.addView(hero);

        TextView tagline = text("DETECT  |  GROW  |  SELL  |  THRIVE", 13, GREEN, Typeface.BOLD);
        page.addView(tagline);
        page.addView(text("AI diagnosis • Eco-friendly • E-commerce", 14, MUTED, Typeface.NORMAL));

        LinearLayout search = searchBox();
        page.addView(search, marginParams(0, 18, 0, 8));

        LinearLayout quick = row();
        quick.addView(actionCard("🔎", "AI Diagnosis", v -> toast("AI crop diagnosis is ready for integration.")));
        quick.addView(actionCard("🛒", "Marketplace", v -> showTab(1)));
        page.addView(quick);

        page.addView(sectionTitle("Why Agrilink?"));
        page.addView(infoCard("🌱", "Smart farming", "Connect farmers, customers and useful farm information in one place."));
        page.addView(infoCard("🤖", "AI crop diagnosis", "Prepare crops for faster identification of common problems and treatment guidance."));
        page.addView(infoCard("♻️", "Sustainable farming", "Promote eco-friendly farming practices and responsible food production."));
        page.addView(infoCard("📦", "Sell directly", "Farmers can list produce and receive customer orders without unnecessary middlemen."));

        page.addView(sectionTitle("Featured products"));
        for (int i = 0; i < Math.min(3, products.size()); i++) page.addView(productCard(products.get(i)));

        content.addView(page);
    }

    private LinearLayout searchBox() {
        LinearLayout box = row();
        box.setPadding(dp(14), dp(4), dp(14), dp(4));
        box.setBackground(round(Color.WHITE, Color.rgb(220, 229, 222), 18));
        TextView icon = text("🔎", 18, MUTED, Typeface.NORMAL);
        box.addView(icon, new LinearLayout.LayoutParams(dp(34), dp(52)));
        EditText input = new EditText(this);
        input.setHint("Search farm products...");
        input.setSingleLine(true);
        input.setTextSize(15);
        input.setBackgroundColor(Color.TRANSPARENT);
        input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int st, int before, int count) {
                if (currentTab == 1) renderMarketplace(s.toString());
            }
            public void afterTextChanged(Editable e) {}
        });
        box.addView(input, new LinearLayout.LayoutParams(0, dp(52), 1));
        return box;
    }

    private void marketplace() {
        LinearLayout page = page();
        TextView h = text("Farm Marketplace", 27, TEXT, Typeface.BOLD);
        page.addView(h);
        page.addView(text("Buy fresh produce directly from farmers.", 15, MUTED, Typeface.NORMAL));
        page.addView(searchBox(), marginParams(0, 14, 0, 8));
        LinearLayout list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        page.addView(list);
        content.addView(page);
        renderMarketplaceInto(list, "");
    }

    private void renderMarketplace(String query) {
        // Search field redraw is intentionally lightweight; marketplace remains the active tab.
        if (currentTab != 1) return;
        // Find the last vertical list in the page.
        if (content.getChildCount() == 0) return;
        View pageView = content.getChildAt(0);
        if (!(pageView instanceof LinearLayout)) return;
        LinearLayout p = (LinearLayout) pageView;
        if (p.getChildCount() == 0) return;
        View last = p.getChildAt(p.getChildCount() - 1);
        if (!(last instanceof LinearLayout)) return;
        LinearLayout list = (LinearLayout) last;
        renderMarketplaceInto(list, query);
    }

    private void renderMarketplaceInto(LinearLayout list, String query) {
        list.removeAllViews();
        String q = query == null ? "" : query.toLowerCase(Locale.ROOT).trim();
        for (Product p : products) {
            if (q.isEmpty() || p.name.toLowerCase(Locale.ROOT).contains(q) || p.location.toLowerCase(Locale.ROOT).contains(q)) {
                list.addView(productCard(p));
            }
        }
        if (list.getChildCount() == 0) list.addView(infoCard("🔎", "No products found", "Try another crop, food or location."));
    }

    private void sell() {
        LinearLayout page = page();
        page.addView(text("Sell on Agrilink", 27, TEXT, Typeface.BOLD));
        page.addView(text("Create a product listing and reach customers.", 15, MUTED, Typeface.NORMAL));

        EditText name = field("Product name", "e.g. Fresh Matooke");
        EditText location = field("Location", "e.g. Wakiso");
        EditText price = field("Price", "e.g. UGX 10,000 / bunch");
        Spinner category = new Spinner(this);
        String[] categories = {"Crop / Produce", "Vegetables", "Fruits", "Livestock Products", "Seeds", "Other"};
        category.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));
        page.addView(name, marginParams(0, 18, 0, 8));
        page.addView(location, marginParams(0, 0, 0, 8));
        page.addView(price, marginParams(0, 0, 0, 8));
        page.addView(category, marginParams(0, 0, 0, 8));

        Button publish = primaryButton("Publish Product");
        publish.setOnClickListener(v -> {
            String n = name.getText().toString().trim();
            String l = location.getText().toString().trim();
            String pr = price.getText().toString().trim();
            if (n.isEmpty() || l.isEmpty() || pr.isEmpty()) {
                toast("Please complete the product details.");
                return;
            }
            products.add(0, new Product(n, l, pr, "🌾"));
            name.setText(""); location.setText(""); price.setText("");
            hideKeyboard(name);
            toast("Product published successfully.");
            showTab(1);
        });
        page.addView(publish, marginParams(0, 12, 0, 16));
        page.addView(infoCard("💡", "Selling tip", "Use a clear product name, accurate location and current price to help buyers."));
        content.addView(page);
    }

    private void cart() {
        LinearLayout page = page();
        page.addView(text("My Cart", 27, TEXT, Typeface.BOLD));
        page.addView(text(cart.size() + " item(s)", 15, MUTED, Typeface.NORMAL));
        if (cart.isEmpty()) {
            page.addView(infoCard("🛒", "Your cart is empty", "Browse the marketplace and add fresh farm products."));
            Button shop = primaryButton("Browse Marketplace");
            shop.setOnClickListener(v -> showTab(1));
            page.addView(shop);
        } else {
            for (Product p : cart) page.addView(cartCard(p));
            TextView total = text("Order total: UGX " + cart.size() * 10000, 20, GREEN, Typeface.BOLD);
            total.setPadding(0, dp(14), 0, dp(10));
            page.addView(total);
            Button checkout = primaryButton("Proceed to Checkout");
            checkout.setOnClickListener(v -> checkout());
            page.addView(checkout);
        }
        page.addView(sectionTitle("Order history"));
        if (orders.isEmpty()) {
            page.addView(text("No orders yet. Your confirmed orders will appear here.", 14, MUTED, Typeface.NORMAL));
        } else {
            for (Order o : orders) {
                page.addView(infoCard("📦", "Order " + o.id, o.count + " item(s) • " + o.status));
            }
        }
        content.addView(page);
    }

    private void checkout() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Checkout")
                .setMessage("Your order contains " + cart.size() + " item(s).\n\nChoose delivery or pickup and confirm your order.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm Order", (d, which) -> {
                    orders.add(new Order("AGR" + (1000 + orders.size()), cart.size(), "Pending"));
                    cart.clear();
                    toast("Order placed successfully!");
                    showTab(3);
                }).create();
        dialog.show();
    }

    private void profile() {
        LinearLayout page = page();
        page.addView(text("My Agrilink Profile", 27, TEXT, Typeface.BOLD));
        page.addView(infoCard("👨‍🌾", "Farmer / Customer account", "Manage your marketplace activity and farming profile."));
        page.addView(profileRow("📋", "My Listings", "Products you have published", v -> showTab(2)));
        page.addView(profileRow("📦", "My Orders", "Track your purchases", v -> showTab(3)));
        page.addView(profileRow("🤖", "AI Diagnosis", "Prepare a crop health check", v -> toast("AI diagnosis module ready for connection.")));
        page.addView(profileRow("⚙️", "Settings", "App preferences and account options", v -> toast("Settings will be added here.")));
        content.addView(page);
    }

    private View productCard(Product p) {
        LinearLayout card = card();
        TextView emoji = text(p.emoji, 42, GREEN, Typeface.NORMAL);
        emoji.setGravity(Gravity.CENTER);
        card.addView(emoji, new LinearLayout.LayoutParams(dp(64), dp(82)));

        LinearLayout middle = new LinearLayout(this);
        middle.setOrientation(LinearLayout.VERTICAL);
        middle.addView(text(p.name, 18, TEXT, Typeface.BOLD));
        middle.addView(text("📍 " + p.location, 13, MUTED, Typeface.NORMAL));
        middle.addView(text(p.price, 15, GREEN, Typeface.BOLD));
        card.addView(middle, new LinearLayout.LayoutParams(0, -2, 1));

        Button add = smallButton("Add");
        add.setOnClickListener(v -> {
            cart.add(p);
            toast(p.name + " added to cart.");
        });
        card.addView(add, new LinearLayout.LayoutParams(dp(72), dp(48)));
        return card;
    }

    private View cartCard(Product p) {
        LinearLayout card = card();
        card.addView(text(p.emoji, 30, GREEN, Typeface.NORMAL), new LinearLayout.LayoutParams(dp(48), dp(54)));
        LinearLayout mid = new LinearLayout(this);
        mid.setOrientation(LinearLayout.VERTICAL);
        mid.addView(text(p.name, 16, TEXT, Typeface.BOLD));
        mid.addView(text(p.price, 14, GREEN, Typeface.BOLD));
        card.addView(mid, new LinearLayout.LayoutParams(0, -2, 1));
        Button remove = smallButton("Remove");
        remove.setOnClickListener(v -> { cart.remove(p); showTab(3); });
        card.addView(remove, new LinearLayout.LayoutParams(dp(82), dp(48)));
        return card;
    }

    private View infoCard(String icon, String heading, String body) {
        LinearLayout card = card();
        card.addView(text(icon, 28, GREEN, Typeface.NORMAL), new LinearLayout.LayoutParams(dp(52), -1));
        LinearLayout mid = new LinearLayout(this);
        mid.setOrientation(LinearLayout.VERTICAL);
        mid.addView(text(heading, 16, TEXT, Typeface.BOLD));
        mid.addView(text(body, 13, MUTED, Typeface.NORMAL));
        card.addView(mid, new LinearLayout.LayoutParams(0, -2, 1));
        return card;
    }

    private View profileRow(String icon, String heading, String body, View.OnClickListener click) {
        LinearLayout card = (LinearLayout) infoCard(icon, heading, body);
        card.setOnClickListener(click);
        return card;
    }

    private View actionCard(String icon, String label, View.OnClickListener click) {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setGravity(Gravity.CENTER);
        c.setPadding(dp(8), dp(10), dp(8), dp(10));
        c.setBackground(round(Color.WHITE, Color.rgb(222, 231, 224), 16));
        c.setOnClickListener(click);
        c.addView(text(icon, 28, GREEN, Typeface.NORMAL));
        c.addView(text(label, 13, TEXT, Typeface.BOLD));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, dp(112), 1);
        lp.setMargins(dp(4), dp(4), dp(4), dp(4));
        c.setLayoutParams(lp);
        return c;
    }

    private LinearLayout card() {
        LinearLayout c = row();
        c.setGravity(Gravity.CENTER_VERTICAL);
        c.setPadding(dp(12), dp(10), dp(10), dp(10));
        c.setBackground(round(Color.WHITE, Color.rgb(225, 232, 226), 16));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, dp(92));
        lp.setMargins(0, dp(6), 0, dp(6));
        c.setLayoutParams(lp);
        return c;
    }

    private LinearLayout page() {
        LinearLayout p = new LinearLayout(this);
        p.setOrientation(LinearLayout.VERTICAL);
        p.setPadding(dp(16), dp(14), dp(16), dp(24));
        return p;
    }

    private LinearLayout row() {
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(LinearLayout.HORIZONTAL);
        return r;
    }

    private TextView sectionTitle(String s) {
        TextView t = text(s, 21, TEXT, Typeface.BOLD);
        t.setPadding(0, dp(18), 0, dp(4));
        return t;
    }

    private EditText field(String hint, String text) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setTextSize(15);
        e.setPadding(dp(14), 0, dp(14), 0);
        e.setBackground(round(Color.WHITE, Color.rgb(220, 229, 222), 14));
        e.setSingleLine(true);
        e.setContentDescription(text);
        return e;
    }

    private Button primaryButton(String label) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(Color.WHITE);
        b.setTextSize(15);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setAllCaps(false);
        b.setBackground(round(GREEN, GREEN, 16));
        return b;
    }

    private Button smallButton(String label) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(GREEN);
        b.setTextSize(13);
        b.setAllCaps(false);
        b.setBackground(round(Color.rgb(232, 244, 232), Color.rgb(202, 225, 204), 14));
        return b;
    }

    private TextView text(String s, float size, int color, int style) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setTypeface(Typeface.DEFAULT, style);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    private GradientDrawable round(int fill, int stroke, int radius) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(fill);
        g.setCornerRadius(dp(radius));
        g.setStroke(dp(1), stroke);
        return g;
    }

    private LinearLayout.LayoutParams marginParams(int l, int t, int r, int b) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(dp(l), dp(t), dp(r), dp(b));
        return p;
    }

    private void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }
    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    private int dp(int n) { return (int) (n * getResources().getDisplayMetrics().density + 0.5f); }

    private static class Product {
        String name, location, price, emoji;
        Product(String name, String location, String price, String emoji) {
            this.name = name; this.location = location; this.price = price; this.emoji = emoji;
        }
    }

    private static class Order {
        String id, status; int count;
        Order(String id, int count, String status) { this.id = id; this.count = count; this.status = status; }
    }
}

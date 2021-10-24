package algonquin.cst2335.zhao0211;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {


    private RecyclerView chatList;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    private MyChatAdapter adt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);
        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));

        EditText editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editTextTextPersonName.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                ChatMessage chatMessage = new ChatMessage(string, 1, currentDateandTime);
                messages.add(chatMessage);
                editTextTextPersonName.setText("");
                adt.notifyItemInserted(messages.size() - 1);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editTextTextPersonName.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                ChatMessage chatMessage = new ChatMessage(string, 2, currentDateandTime);
                messages.add(chatMessage);
                editTextTextPersonName.setText("");
                adt.notifyItemInserted(messages.size() - 1);
            }
        });
    }


    private class MyRowViews extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowViews(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                    builder.setMessage("Do you want to delete the message: " + messageText.getText())
                            .setTitle("Question:")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ChatMessage removedMessage = messages.get(position);
                                    messages.remove(position);
                                    adt.notifyItemRemoved(position);

                                    Snackbar.make(messageText, "You delete message #" + position, Snackbar.LENGTH_LONG)
                                            .setAction("Undo", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    messages.add(position, removedMessage);
                                                    adt.notifyItemInserted(position);
                                                }
                                            })
                                            .show();
                                }
                            })
                            .create().show();
                }
            });
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {

        @NonNull
        @Override
        public MyRowViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            int layoutID;
            if (viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receiver_message;
            View loadedRow = layoutInflater.inflate(layoutID, parent, false);

            return new MyRowViews(loadedRow);
        }

        @Override
        public int getItemViewType(int position) {
            return messages.get(position).sendOrReceiver;
        }

        @Override
        public void onBindViewHolder(@NonNull MyRowViews holder, int position) {
            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }
    }


    private class ChatMessage {
        String message;
        int sendOrReceiver;
        String timeSent;

        public ChatMessage(String message, int sendOrReceiver, String timeSent) {
            this.message = message;
            this.sendOrReceiver = sendOrReceiver;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getSendOrReceiver() {
            return sendOrReceiver;
        }

        public void setSendOrReceiver(int sendOrReceiver) {
            this.sendOrReceiver = sendOrReceiver;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public void setTimeSent(String timeSent) {
            this.timeSent = timeSent;
        }
    }
}


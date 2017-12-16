package com.abduaziz.lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abduaziz.lib.R;
import com.abduaziz.lib.model.SelectableFile;
import com.abduaziz.lib.util.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by abduaziz on 11/6/17.
 */

public class RVFileAdapter extends RecyclerView.Adapter<RVFileAdapter.ViewHolder> {

    String TAG = "RVFileAdapter";

    List<SelectableFile> list;

    public RVFileAdapter(List<SelectableFile> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_file_selector, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String filename = list.get(position).getFile().getName();
        holder.textViewFilename.setText(filename);

        //if the current file is dir, show path, otherwise show its size
        if (list.get(position).getFile().isDirectory())
            holder.textViewFileInfo.setText(list.get(position).getFile().getPath());
        else
            holder.textViewFileInfo.setText(FileUtils.humanReadableByteCount(list.get(position).getFile().length()));

        //set icon
        holder.imageViewFileIcon.setImageResource(getFileType(list.get(position).getFile()));

        if (list.get(position).isSelected())
            holder.imageViewSelected.setVisibility(View.VISIBLE);
        else
            holder.imageViewSelected.setVisibility(View.GONE);
    }

    int getFileType(File file) {

        if (file.isDirectory())
            return R.drawable.ic_folder_open_black_24dp;

        String filename = file.getName();
        if (filename.endsWith(".jpg") || filename.endsWith(".png") ||
                filename.endsWith(".tif") || filename.endsWith(".bmp"))
            return R.drawable.ic_image_black_24dp;

        if (filename.endsWith(".gif"))
            return R.drawable.ic_gif_black_24dp;

        //AVI, FLV, WMV, MP4, and MOV
        if (filename.endsWith(".avi") || filename.endsWith(".flv") ||
                filename.endsWith(".wmv") || filename.endsWith(".mp4") || filename.endsWith(".mov"))
            return R.drawable.ic_file_video_24dp;

        if (filename.endsWith(".pdf"))
            return R.drawable.ic_file_pdf_24dp;

        if (filename.endsWith(".zip") || filename.endsWith(".rar"))
            return R.drawable.ic_zip_24dp;

        if (filename.endsWith(".mp3"))
            return R.drawable.ic_file_music_24dp;

        return R.drawable.ic_file_not_supported_extension_24dp;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFilename;
        TextView textViewFileInfo;
        ImageView imageViewFileIcon;
        ImageView imageViewSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewFilename = (TextView) itemView.findViewById(R.id.fileName);
            textViewFileInfo = (TextView) itemView.findViewById(R.id.fileInfo);
            imageViewFileIcon = (ImageView) itemView.findViewById(R.id.fileIcon);
            imageViewSelected = (ImageView) itemView.findViewById(R.id.imageview_selected);
        }

    }

}

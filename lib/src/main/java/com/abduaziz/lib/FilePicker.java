package com.abduaziz.lib;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abduaziz.lib.adapter.RVFileAdapter;
import com.abduaziz.lib.adapter.RVPathAdapter;
import com.abduaziz.lib.model.SelectableFile;
import com.abduaziz.lib.view.RecyclerListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by abduaziz on 12/16/17.
 */

public class FilePicker extends DialogFragment implements RecyclerListView.OnItemClickListener {

    static FilePicker instance;
    String TAG = "FileSelectFragment";
    OnFilesSelected onFilesSelected;

    ImageView imageViewBack, imageViewOk;
    TextView textViewNoFilesFound;
    RecyclerListView rvPath;
    RecyclerListView rv;

    RVFileAdapter adapter;
    List<SelectableFile> files;

    RVPathAdapter adapterPath;
    List<File> path;

    public static FilePicker getInstance(Bundle bundle) {
        if (instance == null)
            instance = new FilePicker();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_file_chooser, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 11/27/17 init UI
        imageViewBack = (ImageView) view.findViewById(R.id.back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        imageViewOk = (ImageView) view.findViewById(R.id.ok);

        textViewNoFilesFound = (TextView) view.findViewById(R.id.noFilesFound);
        rvPath = (RecyclerListView) view.findViewById(R.id.rvPath);
        rv = (RecyclerListView) view.findViewById(R.id.rv);
        path = new ArrayList<>();
        path.add(Environment.getExternalStorageDirectory());
        adapterPath = new RVPathAdapter(path);
        rvPath.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPath.setAdapter(adapterPath);

        imageViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onFilesSelected != null) {

                    ArrayList<File> result = new ArrayList<>();
                    for (int i = 0; i < files.size(); i++) {
                        if (files.get(i).isSelected())
                            result.add(files.get(i).getFile());
                    }
                    onFilesSelected.onFilesSelected(result);
                    dismissAllowingStateLoss();

                }
            }
        });

        rvPath.addOnItemClickListener(getContext(), new RecyclerListView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = path.size() - 1; i > position; i--) {
                    path.remove(i);
                    adapterPath.notifyItemRemoved(i);
                }

                if (path.get(position) != null && path.get(position).canRead()) {
                    files.clear();
                    new AsyncFetchFiles(path.get(position)).execute();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });

        files = new ArrayList<>();
        adapter = new RVFileAdapter(files);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        rv.addOnItemClickListener(getContext(), this);

        RecyclerView.ItemAnimator animator = rv.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncFetchFiles(Environment.getExternalStorageDirectory()).execute();
    }

    public void addOnFilesSelected(OnFilesSelected onFilesSelected) {
        this.onFilesSelected = onFilesSelected;
    }

    @Override
    public void onItemClick(View view, int position) {

        SelectableFile currentFile = files.get(position);
        if (currentFile == null || !currentFile.getFile().canRead()) return;

        if (currentFile.getFile().isDirectory()) {
            if (currentFile.getFile() != null && currentFile.getFile().canRead()) {
                path.add(currentFile.getFile());
                adapterPath.notifyDataSetChanged();
                rvPath.scrollToPosition(path.size() - 1);
                files.clear();
                new AsyncFetchFiles(currentFile.getFile()).execute();
            }
        } else {
            //todo send it to chat fragment

            files.get(position).setSelected(!currentFile.isSelected());
            adapter.notifyItemChanged(position);

        }
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    public interface OnFilesSelected {
        void onFilesSelected(List<File> selectedFiles);
    }

    private class AsyncFetchFiles extends AsyncTask<Object, Object, List<File>> {

        File directory;

        public AsyncFetchFiles(File directory) {
            this.directory = directory;
        }

        @Override
        protected List<File> doInBackground(Object... objects) {

            File[] childFiles = directory.listFiles();

            List<File> listFiles = new ArrayList<>();
            for (int i = 0; i < childFiles.length; i++) {
                // TODO: 11/27/17 filter files here
                if (!childFiles[i].getName().startsWith(".") && childFiles[i].canRead()) {
                    listFiles.add(childFiles[i]);
                }
            }

            if (!listFiles.isEmpty())
                Collections.sort(listFiles, new Comparator<File>() {
                    @Override
                    public int compare(File file, File t1) {
                        return file.getName().compareTo(t1.getName());
                    }
                });
            return listFiles;
        }

        @Override
        protected void onPostExecute(List<File> fetchedFiles) {

            List<SelectableFile> selectableFiles = new ArrayList<>();
            for (File f : fetchedFiles) {
                selectableFiles.add(new SelectableFile(f, false));
            }

            files.addAll(selectableFiles);
            if (!files.isEmpty())
                Collections.sort(files, new Comparator<SelectableFile>() {
                    @Override
                    public int compare(SelectableFile selectableFile, SelectableFile t1) {
                        return selectableFile.getFile().compareTo(t1.getFile());
                    }
                });
            adapter.notifyDataSetChanged();

            if (files.isEmpty())
                textViewNoFilesFound.setVisibility(View.VISIBLE);
            else
                textViewNoFilesFound.setVisibility(View.GONE);
        }
    }
}
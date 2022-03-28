package com.example.locationnotesapp.DataBase;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.locationnotesapp.NoteScreen;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DataBase_Impl implements DataBase {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteScreen> __insertionAdapterOfNoteScreen;

  private final EntityDeletionOrUpdateAdapter<NoteScreen> __deletionAdapterOfNoteScreen;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  public DataBase_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteScreen = new EntityInsertionAdapter<NoteScreen>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`ID`,`title`,`notes`,`date`,`pinned`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteScreen value) {
        stmt.bindLong(1, value.getID());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNotes());
        }
        if (value.getDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDate());
        }
        final int _tmp = value.isPinned() ? 1 : 0;
        stmt.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfNoteScreen = new EntityDeletionOrUpdateAdapter<NoteScreen>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteScreen value) {
        stmt.bindLong(1, value.getID());
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE notes SET title= ?, notes= ? WHERE ID= ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final NoteScreen notes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNoteScreen.insert(notes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final NoteScreen notes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNoteScreen.handle(notes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final int id, final String title, final String notes) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    int _argIndex = 1;
    if (title == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, title);
    }
    _argIndex = 2;
    if (notes == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, notes);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public List<NoteScreen> getAll() {
    final String _sql = "SELECT*FROM notes ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
      final List<NoteScreen> _result = new ArrayList<NoteScreen>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final NoteScreen _item;
        _item = new NoteScreen();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        _item.setDate(_tmpDate);
        final boolean _tmpPinned;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfPinned);
        _tmpPinned = _tmp != 0;
        _item.setPinned(_tmpPinned);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

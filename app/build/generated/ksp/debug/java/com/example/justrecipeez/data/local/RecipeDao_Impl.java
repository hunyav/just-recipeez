package com.example.justrecipeez.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecipeDao_Impl implements RecipeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecipeEntity> __insertionAdapterOfRecipeEntity;

  private final ListStringConverters __listStringConverters = new ListStringConverters();

  private final EntityDeletionOrUpdateAdapter<RecipeEntity> __deletionAdapterOfRecipeEntity;

  private final EntityDeletionOrUpdateAdapter<RecipeEntity> __updateAdapterOfRecipeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavorite;

  public RecipeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecipeEntity = new EntityInsertionAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recipes` (`id`,`title`,`description`,`tags`,`ingredients`,`instructions`,`notes`,`prepMinutes`,`cookMinutes`,`servings`,`favorite`,`imageUri`,`createdUtc`,`updatedUtc`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __listStringConverters.fromList(entity.getTags());
        statement.bindString(4, _tmp);
        final String _tmp_1 = __listStringConverters.fromList(entity.getIngredients());
        statement.bindString(5, _tmp_1);
        final String _tmp_2 = __listStringConverters.fromList(entity.getInstructions());
        statement.bindString(6, _tmp_2);
        statement.bindString(7, entity.getNotes());
        if (entity.getPrepMinutes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getPrepMinutes());
        }
        if (entity.getCookMinutes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getCookMinutes());
        }
        if (entity.getServings() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getServings());
        }
        final int _tmp_3 = entity.getFavorite() ? 1 : 0;
        statement.bindLong(11, _tmp_3);
        if (entity.getImageUri() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getImageUri());
        }
        statement.bindLong(13, entity.getCreatedUtc());
        statement.bindLong(14, entity.getUpdatedUtc());
      }
    };
    this.__deletionAdapterOfRecipeEntity = new EntityDeletionOrUpdateAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recipes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRecipeEntity = new EntityDeletionOrUpdateAdapter<RecipeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recipes` SET `id` = ?,`title` = ?,`description` = ?,`tags` = ?,`ingredients` = ?,`instructions` = ?,`notes` = ?,`prepMinutes` = ?,`cookMinutes` = ?,`servings` = ?,`favorite` = ?,`imageUri` = ?,`createdUtc` = ?,`updatedUtc` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecipeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __listStringConverters.fromList(entity.getTags());
        statement.bindString(4, _tmp);
        final String _tmp_1 = __listStringConverters.fromList(entity.getIngredients());
        statement.bindString(5, _tmp_1);
        final String _tmp_2 = __listStringConverters.fromList(entity.getInstructions());
        statement.bindString(6, _tmp_2);
        statement.bindString(7, entity.getNotes());
        if (entity.getPrepMinutes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getPrepMinutes());
        }
        if (entity.getCookMinutes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getCookMinutes());
        }
        if (entity.getServings() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getServings());
        }
        final int _tmp_3 = entity.getFavorite() ? 1 : 0;
        statement.bindLong(11, _tmp_3);
        if (entity.getImageUri() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getImageUri());
        }
        statement.bindLong(13, entity.getCreatedUtc());
        statement.bindLong(14, entity.getUpdatedUtc());
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recipes";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recipes SET favorite = ?, updatedUtc = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final RecipeEntity entity, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecipeEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<RecipeEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecipeEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final RecipeEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecipeEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final RecipeEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRecipeEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFavorite(final long id, final boolean favorite, final long updatedUtc,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = favorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedUtc);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecipeEntity>> observeRecipes() {
    final String _sql = "SELECT * FROM recipes ORDER BY updatedUtc DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPrepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "prepMinutes");
          final int _cursorIndexOfCookMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "createdUtc");
          final int _cursorIndexOfUpdatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedUtc");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __listStringConverters.toList(_tmp);
            final List<String> _tmpIngredients;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __listStringConverters.toList(_tmp_1);
            final List<String> _tmpInstructions;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __listStringConverters.toList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final Integer _tmpPrepMinutes;
            if (_cursor.isNull(_cursorIndexOfPrepMinutes)) {
              _tmpPrepMinutes = null;
            } else {
              _tmpPrepMinutes = _cursor.getInt(_cursorIndexOfPrepMinutes);
            }
            final Integer _tmpCookMinutes;
            if (_cursor.isNull(_cursorIndexOfCookMinutes)) {
              _tmpCookMinutes = null;
            } else {
              _tmpCookMinutes = _cursor.getInt(_cursorIndexOfCookMinutes);
            }
            final Integer _tmpServings;
            if (_cursor.isNull(_cursorIndexOfServings)) {
              _tmpServings = null;
            } else {
              _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            }
            final boolean _tmpFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfFavorite);
            _tmpFavorite = _tmp_3 != 0;
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedUtc;
            _tmpCreatedUtc = _cursor.getLong(_cursorIndexOfCreatedUtc);
            final long _tmpUpdatedUtc;
            _tmpUpdatedUtc = _cursor.getLong(_cursorIndexOfUpdatedUtc);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTags,_tmpIngredients,_tmpInstructions,_tmpNotes,_tmpPrepMinutes,_tmpCookMinutes,_tmpServings,_tmpFavorite,_tmpImageUri,_tmpCreatedUtc,_tmpUpdatedUtc);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RecipeEntity>> searchRecipes(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM recipes\n"
            + "        WHERE title LIKE '%' || ? || '%'\n"
            + "           OR description LIKE '%' || ? || '%'\n"
            + "           OR tags LIKE '%' || ? || '%'\n"
            + "           OR ingredients LIKE '%' || ? || '%'\n"
            + "        ORDER BY updatedUtc DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPrepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "prepMinutes");
          final int _cursorIndexOfCookMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "createdUtc");
          final int _cursorIndexOfUpdatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedUtc");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __listStringConverters.toList(_tmp);
            final List<String> _tmpIngredients;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __listStringConverters.toList(_tmp_1);
            final List<String> _tmpInstructions;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __listStringConverters.toList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final Integer _tmpPrepMinutes;
            if (_cursor.isNull(_cursorIndexOfPrepMinutes)) {
              _tmpPrepMinutes = null;
            } else {
              _tmpPrepMinutes = _cursor.getInt(_cursorIndexOfPrepMinutes);
            }
            final Integer _tmpCookMinutes;
            if (_cursor.isNull(_cursorIndexOfCookMinutes)) {
              _tmpCookMinutes = null;
            } else {
              _tmpCookMinutes = _cursor.getInt(_cursorIndexOfCookMinutes);
            }
            final Integer _tmpServings;
            if (_cursor.isNull(_cursorIndexOfServings)) {
              _tmpServings = null;
            } else {
              _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            }
            final boolean _tmpFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfFavorite);
            _tmpFavorite = _tmp_3 != 0;
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedUtc;
            _tmpCreatedUtc = _cursor.getLong(_cursorIndexOfCreatedUtc);
            final long _tmpUpdatedUtc;
            _tmpUpdatedUtc = _cursor.getLong(_cursorIndexOfUpdatedUtc);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTags,_tmpIngredients,_tmpInstructions,_tmpNotes,_tmpPrepMinutes,_tmpCookMinutes,_tmpServings,_tmpFavorite,_tmpImageUri,_tmpCreatedUtc,_tmpUpdatedUtc);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<RecipeEntity> observeRecipe(final long id) {
    final String _sql = "SELECT * FROM recipes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recipes"}, new Callable<RecipeEntity>() {
      @Override
      @Nullable
      public RecipeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPrepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "prepMinutes");
          final int _cursorIndexOfCookMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "createdUtc");
          final int _cursorIndexOfUpdatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedUtc");
          final RecipeEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __listStringConverters.toList(_tmp);
            final List<String> _tmpIngredients;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __listStringConverters.toList(_tmp_1);
            final List<String> _tmpInstructions;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __listStringConverters.toList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final Integer _tmpPrepMinutes;
            if (_cursor.isNull(_cursorIndexOfPrepMinutes)) {
              _tmpPrepMinutes = null;
            } else {
              _tmpPrepMinutes = _cursor.getInt(_cursorIndexOfPrepMinutes);
            }
            final Integer _tmpCookMinutes;
            if (_cursor.isNull(_cursorIndexOfCookMinutes)) {
              _tmpCookMinutes = null;
            } else {
              _tmpCookMinutes = _cursor.getInt(_cursorIndexOfCookMinutes);
            }
            final Integer _tmpServings;
            if (_cursor.isNull(_cursorIndexOfServings)) {
              _tmpServings = null;
            } else {
              _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            }
            final boolean _tmpFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfFavorite);
            _tmpFavorite = _tmp_3 != 0;
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedUtc;
            _tmpCreatedUtc = _cursor.getLong(_cursorIndexOfCreatedUtc);
            final long _tmpUpdatedUtc;
            _tmpUpdatedUtc = _cursor.getLong(_cursorIndexOfUpdatedUtc);
            _result = new RecipeEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTags,_tmpIngredients,_tmpInstructions,_tmpNotes,_tmpPrepMinutes,_tmpCookMinutes,_tmpServings,_tmpFavorite,_tmpImageUri,_tmpCreatedUtc,_tmpUpdatedUtc);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRecipe(final long id, final Continuation<? super RecipeEntity> $completion) {
    final String _sql = "SELECT * FROM recipes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RecipeEntity>() {
      @Override
      @Nullable
      public RecipeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPrepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "prepMinutes");
          final int _cursorIndexOfCookMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "createdUtc");
          final int _cursorIndexOfUpdatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedUtc");
          final RecipeEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __listStringConverters.toList(_tmp);
            final List<String> _tmpIngredients;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __listStringConverters.toList(_tmp_1);
            final List<String> _tmpInstructions;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __listStringConverters.toList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final Integer _tmpPrepMinutes;
            if (_cursor.isNull(_cursorIndexOfPrepMinutes)) {
              _tmpPrepMinutes = null;
            } else {
              _tmpPrepMinutes = _cursor.getInt(_cursorIndexOfPrepMinutes);
            }
            final Integer _tmpCookMinutes;
            if (_cursor.isNull(_cursorIndexOfCookMinutes)) {
              _tmpCookMinutes = null;
            } else {
              _tmpCookMinutes = _cursor.getInt(_cursorIndexOfCookMinutes);
            }
            final Integer _tmpServings;
            if (_cursor.isNull(_cursorIndexOfServings)) {
              _tmpServings = null;
            } else {
              _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            }
            final boolean _tmpFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfFavorite);
            _tmpFavorite = _tmp_3 != 0;
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedUtc;
            _tmpCreatedUtc = _cursor.getLong(_cursorIndexOfCreatedUtc);
            final long _tmpUpdatedUtc;
            _tmpUpdatedUtc = _cursor.getLong(_cursorIndexOfUpdatedUtc);
            _result = new RecipeEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTags,_tmpIngredients,_tmpInstructions,_tmpNotes,_tmpPrepMinutes,_tmpCookMinutes,_tmpServings,_tmpFavorite,_tmpImageUri,_tmpCreatedUtc,_tmpUpdatedUtc);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllRecipes(final Continuation<? super List<RecipeEntity>> $completion) {
    final String _sql = "SELECT * FROM recipes ORDER BY updatedUtc DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RecipeEntity>>() {
      @Override
      @NonNull
      public List<RecipeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPrepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "prepMinutes");
          final int _cursorIndexOfCookMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookMinutes");
          final int _cursorIndexOfServings = CursorUtil.getColumnIndexOrThrow(_cursor, "servings");
          final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfCreatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "createdUtc");
          final int _cursorIndexOfUpdatedUtc = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedUtc");
          final List<RecipeEntity> _result = new ArrayList<RecipeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecipeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __listStringConverters.toList(_tmp);
            final List<String> _tmpIngredients;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            _tmpIngredients = __listStringConverters.toList(_tmp_1);
            final List<String> _tmpInstructions;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfInstructions);
            _tmpInstructions = __listStringConverters.toList(_tmp_2);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final Integer _tmpPrepMinutes;
            if (_cursor.isNull(_cursorIndexOfPrepMinutes)) {
              _tmpPrepMinutes = null;
            } else {
              _tmpPrepMinutes = _cursor.getInt(_cursorIndexOfPrepMinutes);
            }
            final Integer _tmpCookMinutes;
            if (_cursor.isNull(_cursorIndexOfCookMinutes)) {
              _tmpCookMinutes = null;
            } else {
              _tmpCookMinutes = _cursor.getInt(_cursorIndexOfCookMinutes);
            }
            final Integer _tmpServings;
            if (_cursor.isNull(_cursorIndexOfServings)) {
              _tmpServings = null;
            } else {
              _tmpServings = _cursor.getInt(_cursorIndexOfServings);
            }
            final boolean _tmpFavorite;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfFavorite);
            _tmpFavorite = _tmp_3 != 0;
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final long _tmpCreatedUtc;
            _tmpCreatedUtc = _cursor.getLong(_cursorIndexOfCreatedUtc);
            final long _tmpUpdatedUtc;
            _tmpUpdatedUtc = _cursor.getLong(_cursorIndexOfUpdatedUtc);
            _item = new RecipeEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpTags,_tmpIngredients,_tmpInstructions,_tmpNotes,_tmpPrepMinutes,_tmpCookMinutes,_tmpServings,_tmpFavorite,_tmpImageUri,_tmpCreatedUtc,_tmpUpdatedUtc);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

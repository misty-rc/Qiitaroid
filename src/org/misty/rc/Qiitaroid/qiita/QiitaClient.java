package org.misty.rc.Qiitaroid.qiita;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/08/19
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class QiitaClient {

    private static final String APIBASE = "https://qiita.com/api/v1";
    private static final String API_USERS = APIBASE + "/users";
    private static final String API_TAGS = APIBASE + "/tags";
    private static final String API_SEARCH = APIBASE + "/search";
    private static final String API_ITEMS = APIBASE + "/items";
    private static final String API_AUTH = APIBASE + "/auth";
    private static final String API_LIMIT = APIBASE + "/rate_limit";

    private boolean auth = false;
    private String token;
    private String name;
    private String password;

    private Request request;
    private Response response;

    private boolean isAuthorized() {
        return this.auth;
    }

    public QiitaClient(String name, String password) {
        //check token + auth
        this.name = name;
        this.password = password;

        initToken();
    }

    private void initToken() {
        response = postToken(
                new Parameter(Parameter.URL_NAME, this.name),
                new Parameter(Parameter.PASSWORD, this.password)
        );
    }

    private String generateURL(String... path) {
        StringBuilder ret = new StringBuilder();

        for(int i = 0; i < path.length; i++) {
            ret.append("/");
            ret.append(path[i]);
        }

        return ret.toString();
    }

    //API

    public Response getRateLimit() {
        return get(API_LIMIT, null);
    }

    public Response postToken(Parameter... params) {
        return post(API_AUTH, params);
    }

    /*    リクエストユーザーの情報取得

        GET /api/v1/user
        認証: 必須

                input

        なし*/
    public Response getUser() {
        return null;
    }

    /*    特定ユーザーの情報取得

        GET /api/v1/users/:url_name
        認証: 任意

                input

        なし*/
    public Response getUser(String username) {
        return get(generateURL(API_USERS, username), null);
    }


    /*    特定ユーザーの投稿取得

        GET /api/v1/users/:url_name/items
        認証: 任意

                input

        team_url_name (任意) String
        チームのサブドメイン部分 (https://○○○.qiita.com の"○○○")*/
    public Response getUserItems(String username, Parameter... params) {
        return get(generateURL(API_USERS, username, "items"), params);
    }

    /*    特定ユーザーのストックした投稿取得

        GET /api/v1/users/:url_name/stocks
        認証: 任意

                input

        なし*/
    public Response getUserStock(String username) {
        return get(API_USERS + "/" + username + "/" + "stocks");
    }

    /*    特定ユーザーのフォローしているユーザー取得

        GET /api/v1/users/:url_name/following_users
        認証: 任意

                input

        なし*/
    public Response getUserFollowingUser(String username) {
        return null;
    }

    /*    特定ユーザーのフォローしているタグ取得

        GET /api/v1/users/:url_name/following_tags
        認証: 任意

                input

        なし*/
    public Response getUserFollowingTags(String username) {
        return null;
    }

    /*    特定タグの投稿取得

        GET /api/v1/tags/:url_name/items
        認証: 任意
        注意: 認証していても自分の限定共有投稿は返さない．

        input

                なし*/
    public Response getTagsUserItems(String username) {
        return null;
    }

    /*    タグ一覧取得

        GET /api/v1/tags
        認証: 任意

                input

        なし*/
    public Response getTags() {
        return null;
    }

    /*    検索結果取得

        GET /api/v1/search
        認証: 任意

                input

        q (必須) String
        検索クエリ
        複数クエリを投げる場合はクエリ同士を' '(半角スペース)で繋ぐ．例: 'ruby emacs'
        stocked (任意) Bool
        認証しているときのみ有効．自分がストックしている投稿から検索する*/
    public Response getSerach(Parameter... params) {
        return null;
    }

    /*    新着投稿の取得

        認証している場合は自分の投稿を，そうでない場合はパブリックな新着投稿を取得する．

        GET /api/v1/items
        認証: 任意
        注意: 認証時は自分の限定共有投稿も返す

                input

        なし*/
    public Response getItems() {
        return null;
    }

    /*    自分のストックした投稿の取得

        GET /api/v1/stocks
        認証: 必須

                input

        なし*/
    public Response getStocks() {
        return null;
    }

    /*    投稿の実行

        POST /api/v1/items
        認証: 必須

                input

        title (必須) String
        tags (必須) Array of Hash
        `[{name: 'foo', versions: ['1.1', '1.2']}, ...]
        name (必須) String
        versions (任意) Array of String
        body (必須) String
        本文．markdown記法として解釈される
        private (必須) Bool
        限定共有で公開するかどうか．
        限定共有: URLを知っている人のみアクセスできる．
        gist (任意) Bool
        コードブロックの部分をgistに投稿する(public)
        default: false
        tweet (任意) Bool
        投稿のURLをTwitterに投稿する
        default: false
        team_url_name (任意) String
        チームのサブドメイン部分 (https://○○○.qiita.com の"○○○")
                自分の所属するチームに投稿する*/
    public Response postItems(Parameter... params) {
        return null;
    }

    /*    投稿の更新

        PUT /api/v1/items/:uuid
        認証: 必須

                input

        title (任意) String
        tags (任意) Array of Hash
        body (任意) String
        本文．markdown記法として解釈される
        private (任意) Bool
                     限定公開のものをpublicに変更のみ可能(falseのみ指定可能)
        team_url_name (チーム内投稿を編集する時のみ必須)
        チームのサブドメイン部分 (https://○○○.qiita.com の"○○○")*/
    public Response putItemsUuid(String uuid, Parameter... params) {
        return null;
    }

    /*    投稿の削除

        DELETE /api/v1/items/:uuid
        認証: 必須

                input

        なし*/
    public Response deleteItemsUuid(String uuid) {
        return null;
    }

    /*    特定の投稿取得

        GET /api/v1/items/:uuid
        他のアイテム取得APIとの違い: コメントやraw markdownも含まれる
        認証: 任意

                input

        team_url_name (任意) String
        チームのサブドメイン部分 (https://○○○.qiita.com の"○○○")*/
    public Response getItemsUuid(String uuid, Parameter... params) {
        return null;
    }

    /*    投稿のストック

        PUT /api/v1/items/:uuid/stock
        認証: 必須

                input

        なし*/
    public Response putItemsStock(String uuid) {
        return null;
    }

    /*    投稿のストック解除

        DELETE /api/v1/items/:uuid/stock
        認証: 必須

                input

        なし*/
    public Response deleteItemsStock(String uuid) {
        return null;
    }

    // inner method
    public Response get(String url) {

        return get(url, null);
    }

    public Response get(String url, Parameter[] params) {

        request = new Request(RequestMethod.GET, url, params);
        try {
            response = HttpClient.execute(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }

    public Response post(String url) {

        return post(url, null);
    }

    public Response post(String url, Parameter[] params) {

        request = new Request(RequestMethod.POST, url, params);
        try {
            response = HttpClient.execute(request);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }

    public Response put(String url, Parameter[] params) {
        return null;
    }

    public Response delete(String url, Parameter[] params) {
        return null;
    }



}

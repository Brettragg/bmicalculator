module Main exposing (..)

import Browser
import Html exposing (..)
import Html.Events exposing (..)
import Html.Attributes exposing (..)
import Http
import Json.Decode exposing (..)
import Round
import Json.Encode exposing (..)




-- MAIN


main =
  Browser.element
    { init = init
    , update = update
    , subscriptions = subscriptions
    , view = view
    }



-- MODEL
type alias BMIData =
 { height: Float
 , weight: Float
 , bmi: Float
 , category: String
 }

type State
  = Failure Http.Error
  | Loading
  | Success (List BMIData)
  | Standby
type alias Model =
    {
    height : String,
    weight : String,
    state : State,
    baseUrl: String
    }



init : String -> (Model, Cmd Msg)
init url =
    (
    {height = ""
    , weight = ""
    , state = Standby
    , baseUrl = url}
    , (getList url) )




-- UPDATE

type Msg
  = GetBMI
  | GotBMI (Result Http.Error (List BMIData))
  | ChangeHeight String
  | ChangeWeight String

updateState : Model -> State -> Model
updateState model state =
    {model
     | state = state
     }

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
  case msg of
    GetBMI ->
      (model, getBMI model.baseUrl (String.toFloat model.height) (String.toFloat model.weight))

    GotBMI result ->
      case result of
        Ok bmiData ->
          (updateState model (Success bmiData), Cmd.none)

        Err error ->
          (updateState model (Failure error), Cmd.none)
    ChangeHeight newHeight ->
        ({model | height = newHeight}, Cmd.none)
    ChangeWeight newWeight ->
        ({model | weight = newWeight}, Cmd.none)



-- SUBSCRIPTIONS


subscriptions : Model -> Sub Msg
subscriptions _ =
  Sub.none



-- VIEW

viewInput : String -> String -> String -> (String -> msg) -> Html msg
viewInput t p v toMsg =
  input [ type_ t, placeholder p, Html.Attributes.value v, onInput toMsg ] []

view : Model -> Html Msg
view model =
  div []
    [ h2 [] [ text "BMI Calculator" ]
    , viewInput "number" "Height" model.height ChangeHeight
    , viewInput "number" "Weight" model.weight ChangeWeight
    , button [onClick GetBMI][text "Calculate BMI"]
    , viewData model
    ]


viewData : Model -> Html Msg
viewData model =
  case model.state of
    Failure error ->
        case error of
            Http.BadUrl url ->
                div [] [text ("Error: bad url. Provided url: " ++ url)]
            Http.Timeout ->
                div [] [text "Error: timeout."]
            Http.NetworkError ->
                div [] [text "Error: network error."]
            Http.BadStatus status ->
                div [] [text ("Error: bad status. Status: " ++ String.fromInt status)]
            Http.BadBody body ->
                div [] [text ("Error: bad body. Body: " ++ body)]

    Loading ->
      text "Loading..."

    Success bmiData ->
        if List.isEmpty bmiData then
            text ""
        else
            table[]
            (List.append [
            tr[] [
                th [] [text "Height"],
                th [] [text "Weight"],
                th [] [text "BMI"],
                th [] [text "BMI Category"]
            ]
            ](List.map viewBMIData bmiData))
    Standby ->
        text ""

viewBMIData : BMIData -> Html Msg
viewBMIData bmiData =
    tr []
          [ td [][text (String.fromInt(round bmiData.height))],
            td [][text (String.fromInt(round bmiData.weight))],
            td [][text (Round.round 1 bmiData.bmi)],
            td [][text bmiData.category]
            ]

-- HTTP
createBmiJson: Float -> Float -> Json.Encode.Value
createBmiJson height weight=
    Json.Encode.object
        [ ("height", Json.Encode.float height)
        , ("weight", Json.Encode.float weight)
        ]


getBMI : String -> Maybe Float -> Maybe Float ->Cmd Msg
getBMI baseUrl height weight=
  case height of
      Just a ->
         case weight of
             Just b ->
                Http.post
                {
                url = baseUrl ++ "/BMIREST"
                , body = Http.jsonBody (createBmiJson a b)
                , expect = Http.expectJson GotBMI bmiListDecoder
                }
             Nothing ->
                 Cmd.none
      Nothing ->
          Cmd.none

getList: String -> Cmd Msg
getList baseUrl =
    Http.post
        {
        url = baseUrl ++ "/BMIREST"
        , body = Http.emptyBody
        , expect = Http.expectJson GotBMI bmiListDecoder
        }

bmiListDecoder: Decoder (List BMIData)
bmiListDecoder =
    Json.Decode.list bmiDecoder
bmiDecoder : Decoder BMIData
bmiDecoder =
    (
  map4 BMIData
  (field "height" Json.Decode.float)
  (field "weight" Json.Decode.float)
  (field "bmi" Json.Decode.float)
  (field "category" Json.Decode.string))

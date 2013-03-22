/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jakobnielsen.imagga.upload.client;

import net.jakobnielsen.imagga.client.APIClientConfig;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadClientIntegrationTest {

    private UploadClient client;

    public static String TEST_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAZAAAAGQCAYAAACAvzbMAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAABIAAAASABGyWs" +
            "+AAAACXZwQWcAAAGQAAABkADeQEDTAABUt0lEQVR42u2dd7gc5Xm+7z1FvaECEkhCDdFF781Ug3vvPU6vjkvilJ9rYsexE9spTu" +
            "LuOO7GphmMjQEDtukgmhCoSyAkigpqp+z+/nhmPLt7tsx+O3P2SHru6zrXabuzU7/nbd/7FUqlEsYYY0yrdHV6B4wxxuydWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMEBYQY4wxQVhAjDHGBGEBMcYYE4QFxBhjTBAWEGOMMUFYQIwxxgRhATHGGBOEBcQYY0wQFhBjjDFBWECMMc" +
            "YEYQExxhgThAXEGGNMED1pXnT4DT9obau9vRQ/+llKDy2H0aM6fYxZMwmYAxwKHAiMi/6+E3gOeAJYCzw1jPs0ETgYmAdMA6ZEfx" +
            "8Eno32ZTXwJNDfwXO3b7JzF4VXXkrXO14LO3fl8QmTSe65GST33HPABuBxdN/tzcwCFkbfpwLdQB+wGVgZfe3o9E4OYdxYit+8nN" +
            "L3r4FxY4f94x9fvaqjh59KQEYIPUAho20NAsUWXn8YcBlwEXAcGqzrnbsSsA14BPg1cA1wC3oYsuQw4FLgYmAJevAaqfVO4DHgdu" +
            "AK4GZG4gNpYuYBLwZeCJwAzKT+PbcNuB+4Evgh0GxU6c1wP1t9lmIKwJnAK4CzgaOQcVaLAWSU/Qr4LnBt9Ln16EIClAWl6PNNDd" +
            "IKyCuBv6PxRQNdtE+hiwxwGvCfdd7XBfwRcEfKffgRcGxGx/0Z4N9SvO78aB8vA8an3HYBWYynR1/vQQ/3vwPfQgN5O5wP/ClwSQ" +
            "v7BLJaj4u+fg8J3H8DXwG2t3tCW+C9wJtofi+1SwHYBWxBVuxGYEV03Kui30ciJwB/jp65SSnfMwk4J/r6O+DLwD8Bm2q8dipwHf" +
            "Kes+D3gZ+28PoxwJuB3wHOSPmeHmBB9PUWZJj9MxoTavHH6D7LglXo+d9d87+FAnSn0qovA8eT8X2/aN78IXsEPI/u+40o6lB+3z" +
            "8X+lm1vJ20AjILODHlaw8p+3lqk/dNaWH/D42+smBqk/8vAT6CrKMsOA74Irqx3wfcELCNw4B/BF6T0T4dCXwWicn7gZ9ktN1GjA" +
            "P+DJg7DJ/ViM3AMuBn0XHf3eH9AYWo/h8aIMe0sZ3JwF8CL0fnuvq6dqNQUbNnIC0TWnjtK6JjPKHNzzwDuBwZZH8GPFP1/6lkN1" +
            "ZAvchHAejrhyc3QVfDdPLRwDvJLoISyjrgPuDnSPQfbXeDaZPorbhw5a9tpratuL5ZhoDq7Vc38FfArWQnHuUcjy7c37X4vnch9z" +
            "0r8SjnKOBq4MM5bLuaC+i8eIDyCOcAH0Ue8K3IKs4ytNMKrwd+A7yb9sSjnIUopPUXVX8vke2zlOYZnoos8B/RvniU8yZkjC2u+n" +
            "uWVn79c1UoQF8fpRVroKehF/IaOi8eICPlpcDngHuQCF/SzgZdhZUwE+UGPomS0nnRDXwM+I8Ur+1BobYvA9Nz3KcC8CEUbsyTN+" +
            "W8/RC6gLOAbwK/RCHC4fzsTwHfQXm1rOkG/hUZRZ3iGOAmZATlwXEoz7iw7G/DM1gXCrB7DxSL+rk2o4DXDcv+tMY4FCb9KRL2I0" +
            "I2YgERRwK/QEnL4eKPUEiqHlNQQvRPhnGf/hCFtfJgJkr6j2ROB65HnknejEe5wvcPw2d9EnjDMHxONRcgDyGr3GU9FqFnZUr0e9" +
            "YFK7Xp7aW0eh08tblRHuQs5OWPZF6BIhxvb/WNFhCJxzXR9+Hmg9S2TqYAPwZe1oF9+nOUF8maVwAHdOB4WqUH+Hvgf8kvpDUO+D" +
            "75hCTr8d9ooG23iCMt56MQSVbJ+mYcB3w++vnpYfpMeGaLgoL1ecuw7Ut7HAB8Dd37qdlfBST2NxeiJOP8NrbVLp9HRQoxY4BvA+" +
            "d1cJ8+TaBL24CRGL5qxFuA/0r96sHUYfcu9KBeNszHMwmFQ3sJK7tthZOQeEwe5mN8K/J6tg3LpxUKlB5YBqW6CjIVeMkwn4N2+S" +
            "gqTEjF/iogu0mswHkd3peDgL8t+/3zdD7UMxHFzrOKJR+LwkN7G+9CJdON6e6GmQcqFt6cTwCv7dDxXAq8g2zn/1RXch6MnqspHT" +
            "rGfwJmD8snlUqNxAPgRQyfB5Yln0Zzc5qyvwpIFzpJWVaEtMM70I32NuB3O70zEZeSnZX8OjpX4dQu/8jQKp+EUglG9VA4YkEaAX" +
            "kd8IEOH89HyC5hvwV4oOz3bjSvqJMe/ckMR16puxue3ULp0RUwqu783b0lfFVNL/AFUswz218F5I9QwnikMB74OqrIGUn8Ne3fI2" +
            "MYmVUoaZmA5i7UpwQMNA1hzSVd5V3eTKa1CaiN+ACaTxPzfjRzvtPMan8TTSgUYMdO2NNXz09fTGfD0O1yDJrs2ZC0g0MrLU/2hv" +
            "YoI2EuQjWXonDWSOIcUrqyDTiXRhb83sFr0GSwdvgc+ZZiDzdXo8mxMUtoJrT7EqN6KT26ErZurzeJ8NVkN6enU/wFTaY0pB3sNy" +
            "FXtdTkdQVgI7290L2/Ojf7HO9E8yNC2Vvd+HJGR+fhfYHvfzX5TEztFNupbBVSQCHh4e8m2ClKJXhuS735Hz1ocujeznwUxv5evR" +
            "ekE5DRo36IykobC0ihUIBCsbRiNWx+Nm2PGDOyeTGqJnk24L0z6HxBQFa8DHUQqOyJ1D9AYcFcCrNnQX/Nhg1jgY93eucz5vPA8r" +
            "LfX4Waeu4/DAxQWvpoPe/jdFRWvC/wWtoVkOIn/iNdR8oCUILS8pWwaw/0dOfdMM/kzwzgBagss1VeGr1/X2ARqia7s+KvpZKWLB" +
            "g1Cvprdsp/J9mXRHeSTSTzLUAzrT/U6Z0adopFGtjTb+707mXI2aiibkutf6YSkNLdD/QiS6pZCEuM6oWe7iL5tgQxw8fFhAnIvh" +
            "C+iimgh+nOIf+pX845HjU23Jf4ApVdfl9H/jPNRxa9PZTWPUlp1XroHVJceACdmQCcFzNRfqtmGDtdCGvM6NfSuO1GLYokC9+YvZ" +
            "szUYlmKx7lkaiNw77EqUP+UixSOP4o6O2BgSFO+hup7NGUB3tQq+516BotIr+ihaeRgMT00MKkszbYiKq9tiFreAmdm2eisNXzO+" +
            "RxDs31XkI+fc06ySm0JSAq/cuyPfLewGa0jsezqC76cEZGT5sHUfx5gKRdflatueuxCF3/lS285500XuBqb+RwVLmYTPgoFrUS3d" +
            "Bkahf5tISJGSBptPkwSXRgNPIYP4a6P2fJd6lcafM8NLjkxSOoj9c1VLZsn4PWIXk/nbjHursp3f8w9PVVr0IYr3G0r1G3AjGtgO" +
            "xPuYxNwD8w9GEZjSzxT6CFsoab76PZ4XdT2SxuNrIC30t+83rGIfFMKyAFZK1+kXzbZpSQcTMLDSp5W/uz0JLBm3/7l94eGD+uVg" +
            "jrTDSpLQ+eRG07aq0rsweV2N6C7uGs5mUMAl+t+tu7czo+UIfiP6R27H0dKmi4DSV4W1mTJKOzMVjrmo9Hz+cy0ob7w5lGsox13h" +
            "7Pgnr/2BvmbAwnd6IE2GM1/rcHuBG5qJcDFw7TPhVRPXa9FRTXowldjwJfynE/jkUDUxpKDP+kyAmoB9N7UfI+D6ajGLcEpFiEiR" +
            "MoHLmoVgL9zeTTVnwrWizqzhSveyPqsppFEv8OtIZETJ7dlX+A8mfNDNdrUauZrzbdYpYMDFA4aQmla2+WiCTe53aGP+c1Hc3X+l" +
            "t0/+fBIcjTG9Ll2JM1Eh5Bya/HmrxuG2o9sqnZBjPivaRbfvfLqI1EXhw+TMcbyvNonfeXoWaFedBDrd5GQy3RyeSXSH0PzcUj5j" +
            "myG9C+S6VV/TLyyUM8ijybtFGPrwFX5bAf9enrp3D0YgqnLNF6IJ3labSex/nkt7LmVOoURFlAxB40bT/tOtnrSTeot8sVtLY+x8" +
            "fQQJoHI3H2fj3+X47noXI2ee3qq7PJJ6xwHa1b29eiBZ3aYTfKQ5TzihyOr4Qma25t8X3/QP4dhqv2tETXiy9QCXcx72hVKrajcu" +
            "o8dmYMdZZisICIbwK/bvE93wZ25bhPu2l96dvVaIWxPJjF3pMUX0dluCVLkhblff0UFi+AqQfAYMX4lUer9kHUCDGEr7X52fcCK8" +
            "p+P4h8uivfSPowaTnV4bX82dMHhy+kcPJxsKfjXkjMryjPz2XHaOyB1KWPsFX4VgBLc9yv61DFVav8OKf9mUF2TfiGgxXtb6ImiY" +
            "CUSjB2jOY9JZ7IKPJZFvdmtG56CDfQ3hoZP6PSsj2DfBYHC11SucRQDyl/SiUKL74ARvc2a+s+XDyHjKes6aLOlAwLCNxO2EANsn" +
            "zy4nuB77sHlXjehSpZror280nSdBOoz1jyLxfOkidy2m7lMzNhSAXWYvKZh/G/bbx3PcrxhXJj1e8X5HB8q2nPe/4Z2a5z0py+Pg" +
            "pHLKRwynEjIRcS80z7mxhCgTqVbq7C0oqEoTRLuIeyjfC49Qbkyt6L5q9sIhHIGSQTzWbRWpXQaIZ/hbl2yCsmPu23PxUKFI4/ul" +
            "pATif752oL7YcmlxJWfh43Uo3pJp/w1bW0l7e6H1UhnooqBvMv7S0BxRKFF19I6Y6l1RVZnaLV/FErRzsEC4g8kFA25LRPy0if0K" +
            "9mKwp/LUIdQbtQpcZqNI/jN9HXbDS34zDStZzpZu8KYeUVU6j0QIaGLk5NvaX03Ik8yHZ4PPB9D1Np1c4inwm17RhyAP3I8PolMp" +
            "5OQUKSb0v1vj4KRyyicMpxlG67UyHNzjKssbT9XUB20V6s/Omc9msj7d0Ij6MGiPGAPyf6OhsNRI8ikfoJWiv7KPSwNVuPZPgnbL" +
            "XG2OiYS8Oyr0N7YBXIfvY3KP/RLqECdFfV70eTvSGxrcbntMoAmg9xEBKRa4H7kKAfgwygfCgW6XrJBQzedb8qsrqG1QuJ8xNxr8" +
            "K8WurXjD7s7wKymbA25THtvLfZfrXDDmovXlRA5aUHo0RoXAhwJ8qdHIZao9RrW9PpTGEXmoexONrXedHXHCSE45FwlKLf82NwEA" +
            "6aTmHB3PI27tOR55c1rVYI1qIv8H3VhSLH5HB8y6js+hDCIBKRI9E6Fvei+/pKFII7g7w6FezpgyNUkZWTFzIaPbOHRcewAD2js9" +
            "DAPi76KlIeYs2WmktS7+8C8hyaAxJKXgNqO8lukGfVrEpmDLImj0ZJ1vvQg/wIegBPQoNhpwstFqGY+7lojYXDGQm5mLiNe2Ubk/" +
            "k57Nt2ssm1hSRXiwxNvh+Z8fEBPEQ2z1K8jTFIMI5CInIfmgh5BGoxMzPzIyiWKLz8YvXI6h9o1wvpQg0jTyW57xfR2RUO28qB7K" +
            "srQw0w3BOQ0tGuD9xPa2GG2dHX6cjiXIp6b81BseTF6B4YLt/8KLSQ1cuAE1o8luGjVFI7k2ThtAVkL7jryabrQcgAvQ3lzsrJY2" +
            "2T0CrIZkwGLkJe06+QgbQS3VOnkWWYs1ikMH6ceqP19RPwqBSQYLwStUs6lr3AwE+7g5uRlRC3F+hCblV39H00UsdRyNXpeClCSv" +
            "aW/WyVQcIm/U1HJZonIxG5D/ghmoV+Jvk21RwFvAS1sTifvXM96TzCV+tpz0tuhw1Uzh8ZTz4z7POqZoyZiVZNfAw1YLwNdbQ+A1" +
            "n67Yv+qF6KV98Az26p7tDbjOmo2OVt5FOAkStpBWQdqrPeRWLJlJ/07mhbY5Cqj0fqPw3NHZgUfXU6HLK/0ItEPZRJKOF+HImQXA" +
            "6szWFfxwPvQsKxJIPtPY/CNTtQXHg2eVlyA0M6ss7P4VNW5bLv6dhAZe5kBtnH2IvkV81YTZw3uxdVX16FqszOQd52GL29sGYDpV" +
            "vugNGpH7uDUbv/d7X12R0m7YN1GOqOuYfEGupDoZLd0ddO9PBuR1VEK0luvjFIUGaixM8h6Ga0oORDD3WSXi0yES0KdRwKM7Qzma" +
            "0Wr0ddRLNY0W4zmjD5OBKPeGQ/BHk02a5nMzhIYd5sGDO6vI3JIRmfH5AH0imqK7cOIPtVRreTz+S3evQiS38xCmstBb6FwlpnBB" +
            "3fqB6K194E259P432MQWuGvA+NhXs1aQVkArWreuqxGz3Ez6NS180ojrscTfjpRpZh6FwH05huss1bTUD5kdlkE69eBHwatSXPgg" +
            "eAn6P77jCUi5iM7rk7UQL1RWRZQTRYhNkHy+LcsRN0vvOogBmurs+1qJ7NP5Psw747yG/yWyOmoHviSLR2ym9QVeKZyGBKR28vpd" +
            "j7GNPU+zgX+Az5rRMz7KQVkFYTzWOir2kkll8JCcpTKCS2ET3wJnuGM+HdKm9FD9GMjLZ3D5o4OQ0JUvniNwuRpXkFatIXryyZDZ" +
            "WLCo0hn/bmw2mdV1NdWntg0FYas4v2+nS1y3xkGN2DROQqlCuJ55Q0jpL09lBq7n10ow7RHySbyMCIYTiz/AXkHk4kSTZ+EXklJl" +
            "tGYgK6gLyOLBfceQSJx2xUvVIr/DANJVC/hSZOTiU78SpnLPnMPemkkVU9zymPBopb6HwlZC+qylqEvJGHUL7vXJSjqy0ivT2wdg" +
            "OlW25v5H3MQGv15LXIWUfpdA5ixJep7aV0+rpWMwE1h8xSPJ5B/aEOQJ5Ho9j1FFTh1R+9p915NrUYTT4CsiWHbaal2vvJ4/hGTB" +
            "dCZGy8AhkcE1AX4x9Tr/qwtzfKfeyArpqP3Bx0v+2T4gGdH2haqU232KRnJHkgU9CKaa/JcJtF9HDvRnHsNJP35qLKspW01/+sHj" +
            "3kM1+qk7P/q2ev5+GB5FkaHsqRaEni05CAD1mvmN5eeR+31q28OhS1mD+h0weTJ50WkFZwqCs9I+m6vp3s12p+CPXzOo3WqqtORW" +
            "Wcvyb7hXemsu9NuN1e9XseC4rl1Q6oXcajfnIvpdbz1NNN6bqbYNvz0D3k3weiXEoW1YUjmpE00DTjRtQgLc9VAPcVRkqi7k9R5d" +
            "P/ANczdEAKYQ8qv5xB623Fe9BEyQEU687Suh/JhQuhlJr8ngWdzn804xCqox893ZSefIrirXfVyn2MAr7OfiAesHcJyEJkeX6XkW" +
            "u1jBRGQrjvtcDnUXJ7MSqn/RqaxNVO2OJBVNp6Oo07j/bX+ZzZaMLiI2S7amGnG03urbQz4XU4GJovG9VL6cFHYdv2WrmPjwCXdn" +
            "B/9zCMojwSBpq0XBTt75UoIfsaWpubYoaPQ4B/j36ehkTkWGT1X40MgfNofQbuHiREs0napj+N5oHch8JaK9EEuH6Ue6nV/O/M6L" +
            "W/QiGtkfwc7OvClEdYLD8KBRgsUrrrgVqLR50HvH+Y9uRJ1AL/PlR2vB6VXU9GkZphaTg6kh+cagbRBJ8CSk79AFm5ebUvNuH8A0" +
            "PnDCxCiex7UA7i/1Bu5EzSN0t8FE08OwEZEv+H1n6oNyG1XgvzKWgy102owV4eLcqzIquCiJEaXutkq5bW6e6GJ5+CR1fCqIpI8V" +
            "hkNOWZB9uEetP9ELib2hV600g6hOxBY3xuzUg7LSCthNBiS+wo9DBcgeYAvH4EHIdJOA21vanFKBR6Ogy4laT1yLmorXwzlqIKqv" +
            "+meQO+ZjmJE5HncgeaXNhu3igvTyGr0tksSpfzEKG9Kxzd001p7ROwZRuMq9D2JcjILZJ9amArCgf/N837hhWB76BrtRuNjWeirt" +
            "rZn448NlqD7ci92oJ6ZoEejNCk6pGoRv0mYA15LRRjQvggza2weNb4kegaXo6q7M6lvkd5F4ovhy7NWs145AH9FIXUjm9ze8+iQT" +
            "rrAoasWo4/hQaiVkIb1eKV9fyZLagopieHbedDoUBp6bJqKR2P7udr0X18EdmVPP8S9c56KOXr47zfvGi/VkXbWIgqBTMlbwFZj5" +
            "KmK5BwdJEMLj20V0q5BFmjq7GAjBSOBS5r4fWLUR7kN8gTWI3iyCdWve4O4NVk31hwCQoF3IPCWO08D3mtLZPVrPmdqHCglcq1aj" +
            "HM2lvYgAa8MajN0cinVIKtQzqvvAZ4DzKG7kZifRHtr53yQ+TNt9KNYBzwBpL88DwU6l3JXiQgT6Oe+48g0ViAwhZTowMkOik/IN" +
            "yinBSdJDdkHDm8jdaTomNRt9zDUKn2NehmfwG6vivQ7ODQNb2bffZJaKmCdnMhu5GFn3W/qKw6/HYj6/hk0j/31d5gFmXY5axBHt" +
            "YB7A0C0t0NzzxH6fHV1fmPtyERvBRdr1+gAo4z0OTVkHH2J2gyY6sz9QtURgBmoDE3l55qeQjIXajaZhey8E6m/hKS7brnB6JY+B" +
            "5Gfjngvs44NNCHMht4I/Iqb0PW6Tmo/Uke4hFzLLpn70IhtdAk6G40CGYtIPMy2k4XMuzWUtlwshHVApLlINSPmqrG63yvy3Db+d" +
            "BVgJ27YPee8gqsI9GSBzHHovHuepICj0tprdHmOrROSGibl/J83GhkKO0M3FbjU5Lhtp5Hqnst8g5ej/oPNVp/uF2XfwqqtMnaMj" +
            "KtcyLtr8jXgx7GtyBL7uPAzTnv9zhU3beeVr3h7u7ygWQn+SSE55HNczoBieMjLbznoKrfs2wtvxmdr4WkK6DoPL29lB5crtnnyf" +
            "yPixlqvM4AXofu5ZWokefqFj7pYwzthBxKAUUFcuk5lpWAPIFO0kOoXcSbyT4vUcsynIRizzvyODmmJc7LcFsHox5X9w/Tvh+Pmj" +
            "HeRdpqqu5uSqvWwu7dsYgUkYWfNXPJZr7Tgajty0rSPy+zq37fmPr8NOeJaFtzqT1XZ2/hgjp/7wUuRF75HjR37d4U21uFchZZ09" +
            "/+JoaShYA8gsrGtqG+MS8kn2Z+tayfCejBtYB0njMz3t69DN8cgQko9LCatJZidxc8sRH29JV7IXmEYWaQTRjrIJTj2YZEJA3VK+" +
            "Y9Q3aLP61Bon0AKs3PgnwnXZZKut4J42hevXcUSmpPRfm9m5vs5xXkE27KJd/droDchdodj0Uhq/QrebXOTxla+z82OoZc4nsmNa" +
            "Npv+KkmhsY3lnYx0fHcU/qd1SGsCA/wWu151ctSkiIJqIJmWk4hMrwzGayyYPsRt5MHN4+lmwqhPLtAdc/QOneB6Hnt8GQQ0lX5D" +
            "ALicjhqFLreuqH73+R8V4XyTFHnFZAliPlXEfyUN+GJvLFJyfvheHjGejl9dCjkLJ2ygMZqbN7h5vZDI2Xt8vSYT6GqchafIzwpH" +
            "1eHaMvaH8TTELPyjxk/W9J8Z7ZVA7su2g+kS0Nm5EnMzf6fTrtd2zuIYcy1SEMVrRXW0hry4K/IjrO21HlX7WBtJvmE2RbpYjCVx" +
            "31QIooHv1/KFx1Jaq0OgJ4E/msE1DNZcidv4ok2dmDrI5Ordq2A3cHBl2XrNslZDFQtcqJyChI54UUCtXN9FaRz/oWZ9N+dde86P" +
            "vh6J5N00hyIlrytZyHMzie9eg8H1z2txe1uc2JVdvLh0qPs9US615UWHQmit7cWvX/bWSXPI/ZQzLXJnPSCsjhqMTydPRgx17AII" +
            "oZD8cAfiDKsUxCccINKAbZS+cG8a3Imtvfydr7GKQza4HPRJMbH276+YUC7NoNWys6sq4gn0T6NOCSNrexJPo+F81GT2vpVldIpZ" +
            "0R3Yh16DkuF8WXkswRC+Ewhr+56swWXz+IQnfjUUjpNirnse0h+3D8HlRo1FEB6UZWZi9yhxaiG3Ijqi74Kord5Tmprw/ddK+M9u" +
            "dKlFgfRefaIBTwQleQY7O2NgnJoZyE7vHGFTPd3bD5WUpr1pfHxLeS3/3wrjbeOxNVR4LyhguRF/BcivceX/V7u5VxO1CIsDq/sp" +
            "D2vJCLGP6QcpouAdtQodF1wFfQkgY3oSkIJ5Fdq5qYEpX3/S4kXO2Ic13SxsW6UEz6F8hCezm6+NuQJfMwiuvdiRJLx6A5AXmo3i" +
            "zkBv4QXZQBOicg3ehBfJ7sb4S9iaw7IneRzfkMaRU+Bw1mS1EDuvq9owoFCUkld6EJkFlzXvQVMi/m9VSGmQ8nqXJrFn4+CV2POO" +
            "n7MPKyQq39zeh5mV3jf+9Hz3Wrwj8KeGvg/rRDrakFfciwXYeiM08h0RyFPK4zUDhxJkMT273R39qZszGGymcnFpBcjLy0ArKMRD" +
            "xeUXbgk9ANdiIKKT0cvfbHaFA5JvrKOkeyCNVY/wzd3FPyODkpKKCHYR17dy17u2TdfXQj7YdFJxOWNyige/oxtNZC4/ktxSHFNL" +
            "ehvkhZ0wV8AjWcbMVgGgv8QdXfZqNncjlqjd/Icj8SGW1xTupZ9JyfG3gca9DAWyt/cCpqC/L1Frf5KrKvAkxDLCDPonktq0k8ux" +
            "K6B+cjwTgEjYmNzvVE5NVsI5xZVArTDrIzyIaQ9sHfGu3Yy6hdDlZAN+UlyNW+DN24v0Ru23Vkvwb1yahsOI+kZVpGRV9ZdYjdW8" +
            "l6lusy2jcKFhNuuCxED/69NOpyUCpRuvvB6sTqXeRXFXgG8Nctvuc9DB1cR0fHt57m1ViTSMJfoIHxl20cwwZ0bet5MJ9iaOK+ER" +
            "NRl+ZO8BDq5/cVlJd9FA3UZ6PJ1L+DojXHRcfbLMQ2nvbn/FRPpdhKjmuCpBWQ6ShsNDbFaycgC+6taDr/XPQgfgPdeFnOiLwAWZ" +
            "lPIgtguJmCKj/WsvdXY7XTVibLFh5xWHRJm9t5IeGeUQGta/I8CsvWp6+vWkDWkm8J8odJH665BPi7Ov9bgLy8NHNXLqr6/cbAfd" +
            "+GvMvZ1J+zcSDKq6b1Hv8DGQt50WhuyQpkLByFIjPvQm14zkGh/JAQ/hlt7m/1crpbo/3o6DyQ+bSuYN2oMuK1qNT3EJQ8upLsch" +
            "ZjkJu4G/g5OfV7aUAvsla30hkBa4c+NCP556gNzf8iKyrEU9yS4X49gEIA7yZ9079qxqNQSDssQKHSe6lXWdXVpb5Ieyqa65XQpN" +
            "e86EZFK39L48To65F1XM/om4sMoDTVWBdQOQDdTtis+yeRoTW3yetORhNJG3U3mI5CXXnlPrYhgbiF+uPVfCQYL0LVau2G6gdoz1" +
            "M4gspqvRIygsbT4SqsdsNEh6Ib+izk9mXZ46iIXLT1aKnU4aSIHoYeWmuW1km2obXAvwZ8G8152EHS7vsaWhf4TWQTStyNrPeDkV" +
            "V5WuB2/oBw8YnpQvdrP/VCNr09lFashue2qrVJwpXkszZITDdqNPlrFKJaGJ2zY9GAehWarzWxwTbiaqwNyADqp74BdjiVK9rtIE" +
            "wk16KQb5r5E8cgg/NbKJJxNLL0z0Re1e20bySU0xedi99E5+4rKKH/APVDT1vIdgnbG9FYErqswN9TaTD0o2sVN9LMnLRJ9EdRcv" +
            "BIwmd7FlBCchWKE59INsnXIrKkxqGFhxbQ3MLJikFkCR2IkoOD5LsmcjtsRUnh+5GIzEKW5SJ0TQfQQ1Og9XLIJ1AYq93Fj5ZG+9" +
            "mNrMujUOKxlTkhJ6IHKQvmIGv41+geu4Dqe7ZQgIEh2vkA8lzanV3djCXAvyDhGIeMtFaYhAaZH0XfB9EANBsNYvH8ngJKVJdPfP" +
            "sB8hLTMoi8lumkt9R70fyzN+Zw7krI030CPbvrqEx+z0PPxg4UJlMTrMqZ6Ksz3J94ueZT0NK15//2M9PxZhTpKSdeYqCVnFJLpB" +
            "WQYnRwd6CbtXF5Y+PPOxEtlrKmhc9vtm+jUJz226ha7E2ElXCGfHYBCdZdZDOIZs0uNJjdhRLC85Cbu4jK8/8AGqjPpHUR3IwewN" +
            "BjLyLD4u7o9+3opn89mrz6UtJVZR2HBsOQe7Me56Lrehu6pxLPoqsLtm6n9PByCrPPg4HfpsEGkOWct4DEn7WL1gy7LciDiCuitq" +
            "Ky0i507u9E1+LE6PhHIQH5e5ICgZuRx5o2//AsCgXGZcGdYCcqq12DvKFNyOsaS9Jsck70cxzyWUVcVtzTTeHwRZSW/XYS/2PIGG" +
            "t33fo16HrMRAn48cBngD9N+f6XIdGp5nlkGEzJ64SmHcCPREuK3opcvAdR+d+JNHaTa3EEehgfILuEej+66Gehiq+7aT8Z1QqHIi" +
            "u1nUE0S+LrujTar01I5C5FwlH9APcjgZlOWDlkiWTAaYU9aBBaiuLjRRSqOImkLcVFwNXA71O//cYY4J1oHYWs56TEg+f1yHurbO" +
            "VRKmk2emnI1IXvAh+i/cElzTnspzWD6VY0MJ6KBtSnScrzB5FV/msU6tyICmgORUL+nWgbu4FvAh9N+Zlro/08NOfzUU4/Eq71yF" +
            "t4AglkNxLcY9BzcQj1jY4knNvVBTNnlF/rTahi8FTC2YxCnnGbkzgH8ifoenyA+jnGScB7UUivlihvRc9UlgZVBa1MJDwUqfNykl" +
            "UHlyIX/3jSVWiBXO3FqPQ1y74vg2gAewTd/IczHM3VxCwUZ1xL64NoHmxGCfEHkCi8DA3M9a73o2gAv4Twao1fAr+b8rVb0HVair" +
            "yeMST5pJfWeP2FyHD5EnrY7kee35ToPS8A3k5+17sHhV/jwTahu5vS0mUUXjlkKfgNqJqolTBPCP1okGvlum1BYaQLkDF4BRpgD0" +
            "eD6xwUxvoNist/D+Uh/jz6OfbCvoYm/6UxIlczPP2qtqJ7OQ5LPROdn0noOT0VjWXTCYmA9PWVC0gxOkehAvIscDnyjF7N0NYov4" +
            "uu0RfQdYjP4aLo72+gca5vC7qeuRkxaU9gfMa6kIV6OLLE7kBVPPejUMPRpGupfCQatLIsfS2RPOjfQl7OS9vaYnrGoYv/BCNjed" +
            "1V6OE5Fwl8o2qdAeQ9TEXhyVBupvmM/CeQaCwjmY18QfS37TSetDcdzYE4GoVY5qEHaQA9WN9HA8TZ6P7MmvHROap0NXq6KT25id" +
            "LmZyhMm1KdD/k34B3k1Ak1oh8NZK3cc4egkPQGFCocgwy68vNWQF78RFRY8W00YF1MkkBfh0SkWahlR/RZh5B9S43dyBNYG+1PXO" +
            "k1GkUDTkGCMavtzx4YoLDwUJgwDoqluPLuOuDPArb2JBLubcj7q7ea50Lg08hA3oY8zbSVWs9F56HjAlJNAT3IhyML5nZkGd6HYu" +
            "jN4qIHowEhbaVKgfRx00NRLPw+lGQcLpd5DiqL3UT+re2bMRM97GnaTTyOHr7zae8BW4e80mpTvD86L/eTLGQ0D3lq85HwbEYD06" +
            "wmn3FTtJ3zo6+YXai679couXty9P+s82BDc0PdXbBlK2zcBAdOqxaQpciYybJaqJo+kjxgWpag6rubUb5wNrLYaxk/x6Bx4mqUX3" +
            "oDyjPG4ed/QeHDRobDejT4hc5eL6eIvIoN0T5vQJZ2AXlVi5FXGs+2z64/VrEIM6ZCT2956fat6N5v5Zl/FC393Y8mGqYxeLppvU" +
            "z4OWQA5FLCC+1bRj0ofBX31rkLWYKLkJDMafC+haQvF63u7xJTQspcfZOcgazc26J9GI6k3ZxofzbQeQGZQzrxKCLvYyLtT9wDxc" +
            "RjAdmKwlQPoNDPBCTsS0jKOH+KQgAnU1kmWou1SCAWM9RTGRttYzEa3G5HovQSsksgltBgXXWvFbTQ0EPLKZx4bPWKdaBy21eRX6" +
            "+02ANpZZCYhsIuN6LrcwQSiA3UDokcgYTlChRhWEJS8LAaeVofbPB5K6L9CzXmtqNcTByW2hwd9wSU+zwBicYM8iyeKZVg9GiYOh" +
            "nWb4y7MG9HRkua9jVFlFe6BeUlXtnGOWlGHxLtGeRYGZqVaz0WCcbRKKx1HwqjLEGhrVqx6YW0v4JYkdrzDw6IPvdGpPbD0adqOr" +
            "op1pHNCnLtkHZOxgp0nc4hGzf3KvSA7EGewvPoAb8AleROiV7Xhyyw+5EBcgmNLcVBlGMZjfIh9QyCSSgccBDyVr6DLLxmnk0a4p" +
            "LIofvZ3U1pxZpkedvKhPpjKATx4Qz2oRaxgLQ6cJ6GvM8bUaHCWHQv1Iupz0d5kJ8i7+4hksq4T6GKuVrvjWe7xx5BGvagXNNaJB" +
            "obURisFz1nJ5KEpYavielgEaZMonDobEqr10Pvb4fPLwF/TONrsAWF+x9GE6wvI98efjtQbiXXtZqyjs1ORqGIY5FleR8awE+Ovs" +
            "oT7dOROmbRR6qboYPKHDTg3Ik8pLy9kLgUcCMKqaQtKugUceXUOLJbing7qgp5M8nEtoVUPljbSRYFOx0NXs2uzWPI0j2PdFVWZ6" +
            "B78WrkEb+G9pO3W9FgONSa6+2B5avgiafgkIOgf4hj/U+okCGPAovdSNRazbvFpe/fQkJ+AM3nMh2Mkr0HooHwJ9Hft6AE+1U13r" +
            "MGhVIaVUUWo9dsQNc5DktBMidjHokI5T3XajVKcB9LtSiUihSOPZzSTRVzlh9GxQVvqbO95aiKbxsy1s4h37wY6DnbQ86FRHkdxE" +
            "xkCR6LLNKb0Ek+PfpbPGBkYT2U0EWuHoTuRyGyp6KfT6jx3nZFpdoaXYAGu+cY+QKyBuUkTiVbS+hu4B+pzFHEbENVJ+uRV3J2iu" +
            "0NIq92Mq0NwEeh+/snqDv0q2h9AaByNqP7aWhosKsLtu+g9MAjFObNriUgu1E11i1k39QuDquFxLnnIE/kDmRIxK3IG3lsk1Drjo" +
            "NQUcR90d+vBj6HhKSch9BzXp0XfR4ZW6vR/bA5+vxx0baPRV7GjMBjC2V7dCzPoTFrLuWCNViE2bNg7Bh5mkkLm39AIany69uPrv" +
            "lvovP2avIp8KjF1uj7XuWBVLMQWQ4PoJN4ZfTzWcglbjfB1Y9uulo32Cmo0mELShY+gVzHKSRddJ9v8/N3UNk+ZQu62R5E4jSRkb" +
            "vY0p3Iam1lslsawe1HA8n5Nf5+DRooXkxtQa/FKhTKOI/Wz+Xi6PN+gu691xL+QK1DRkFtERrVQ/Hnt9L9gjOht1tVOpXci8IcXw" +
            "v8/HrsQdelkVU+gJajfkuN152NvMGt0evW0DzkV0ARhStQKCaeG/N+FDZ5ARKNUUgg5kX/34Cu5Xpk2G1DY9A05AXHYamQcOrdyN" +
            "hoZ04GKHoQz1d5niTZLfoHKMyfA/PmwGMrYdRvHZRlwD+ThCo3ouUmVqEQ+sUM77ITy9B1ym0OCOQvIKAb9nj0MN8VfX0HDfDtDu" +
            "Dx6lu1BraD0M39A+Qixz24utGNPRblAEJWrYvZiSyMeF/ibS9Fltl4kiTfwrxOcADr0aBxPOkH1J2kv15XAF+kcl5IvG7KAtKLRw" +
            "lVC40jLMm/C1mGr0XifiWK1bdq0Q6gwW8W9Vbd7OmBDU/BijWw5AjYXbOt1NfRYPrhgGOpx270HDd6lr+IhOvtNf63HFndcaXjau" +
            "SVpDHu5iIBigWkH03mfBYNwLujbW6MPn8HuqYF5Mmdjoy6dge5fiTOH6B9ARkd7eMc5AV9gfJin1IJRo+hcOLRlB55rDrA9SkUFj" +
            "wQhfMGkXCcxvDOvl+BJhf+HjlPZB3OgxqHyvjeji7MA7S/BG5chVUvGT8bDVh9yMp5Gbrhj4v+Pq/Nzz8AhSZ+F/X+fxsSjF5k2R" +
            "+AwkQ/pLW1pPNemvMONOCcnOK1G1Hy7xu01nH4vdHnxExC1XmPIW80DU+h83cCYYPMnyEL8myUfF8b/d5qo8On0aA4l3r3WqEAgw" +
            "MUr/lFFNqou62PoIEmK/poLCBLkWdQK8G7FV3bsWjg7EIe21bSU929eRfydrrQM1+I9nE6et4mRf/bivKj9yODpp0O3X+FKu9a7Y" +
            "pRi8nIe3o62v8pVN8vAwMUjj9aFVmVBRO7UKHBt5GIvAHlfoZznN2FcpDL0TnPNV80HB5INdNQeeUGVPbZDgPI+mh0gWYiC2krGt" +
            "DLO11uoT0PpB+p/S5kbfVFn1NCltwekvLPh1GVWtrt5sUT6ME9jvptV+JW70tJegEtQtcubdHDdmTtX0cyw/lS5In8HHkBx5e9fn" +
            "f0nh3Ra3ZGn11A8ejrys5LnDQulP1eRAnDE6Pf/x5Vx4yK3rcECdKv0YPVyrKza5GhciiN7pfRoynd+yClBx+lcMzhtUp6Y/4KCf" +
            "OnaP8ZjBP7tQaKR1Fcfge1k+x3Rv97MRL11eiZWkd74ZYnUbv5P0DivRzlTSaR5D7Wouv7K1RuPwMZdYuRp5d24Ps08K9tnkPQ/b" +
            "MDhdV60XPytehc9FLe2LC/n8K82RSOO5LSXUthTMWpfRIVbtxGzuGjGvShMOXtKPoxJe8PzEtAYmui0fYPIXxd5Zg4hNUoJBHHEu" +
            "J+T4dE+/dzlBspEC4iz0fbiB/g3uh73GQxnuU7nvRzQ+IJcXlxDxLcWmGkrUjoHkSD7XgkuEui/f9qi5+1Gg1OP0EDwziUSLwczd" +
            "eAZKLhM9H5jO+dXpLk8BMM9RrKr9kgSUn3U6g09dPR/wpl7z0/+pxbkIWYNqG5Bg1+k2nkvRQKMDhI8Zob6D7m8FolveX8K7rW/0" +
            "l74c24D1a1Z3Qj8vbjdTuqd+T56PPnRZ9fRM/GfdH1SNuVoN752ILmh2xAIhKHUiYgY2QRul4bkRH2OBr4bkdG32IU3qq3sNQgmn" +
            "vyz2V/a2RIDqABdg+JkbIdCcaW6Cs2XOLneHZ0Tj5bsaUSMGoUhTNOpHT7vbU+6yHUgeAbZOMVpeH56DMvL/tb7q2cshSQx6KvZ0" +
            "gqQ0ahQWMSSRvnqWhgysKtiz2QescxGO3Tgejhf5wk0b2S9luOTEZhq/gBHoNuyK+SLPGblqeQGK2ntTbOrbARCUS8znXMehRSXI" +
            "YerpkolnsElVZMyBoXK1Ac+JvI6h+PhPUJlGQsoEFlBkn79slI6JahlQUXUF/oC+geGB2dv/dHx1KLHpQX+xYqqzyI5lbabmRVxk" +
            "sEND4Ho0fBPQ/JCzm2bi4k5no0f+pvUBg0pBNAP4nxAhpIPgN8ksoOxtXW8DI0gMb36GHR11NIdNKWojcKd+1AC5U9TDInrJx4bf" +
            "RDUGHNU+h5XY6u5W3ReT8C3QOxCN2D8h031Pi8Z6J92h593xb9vDP62o3GhTgXEy/3OhHdDzOR6K1A994KakUEdu+hcMIxMOdgeO" +
            "rp8jkhMT9GXtfXaX9tmmY8hlZDLG+3HzJzvWWyaqf+M+QOj412Oo639qNBayWJqIxFD+08FJpISy3BGaCxB3I3uhlejG6MZci6Ph" +
            "slVu8CPt/GsY9iqIU0JToHT5LcpI3oi87db6LzdQG6Lt9rY7/qEc8ePjP63MdRDHoNiccU54faneRZzloUvno/Eq+n0IC2HQntq6" +
            "iM0e+M3jOX9JbwrcgifaDJ6yaj6381GsBPQQNKPJ9iLElCeUy0rzvQ4BJ7vPUpFGBggNI1N1A4JlVj403AX6C1WP4Qlb+3Um7cFR" +
            "3TVpRr+2ydc1AuTkU0qE9j6OC2EA2aG2m+jkQcnm3G3dE5f1V0jGcz9P7qRvNMDo7+v55k/s8tKJ82Ovr+FWq3978W3cvxOvaxUT" +
            "cmOv4ZSIQmlH3FrT6qjcll0WfUXq65WIQDJtP1wvMofvk7tQQEdE+eF12TV6c4TyH8LxLT6nxyLyNIQBrFI1cit/cUkl721exCls" +
            "Cm6GszuknTJuvqzTjvik5UrUqDNSgUdATJ/IEF0eeejW6meg3M0lKo87f5SKgGaDwQr0cPx5PRe16APLRH29yvWtf1eRRzPjT63A" +
            "fRdZgYnY9jaR6zbSchtxMlkA9FFTO/iwaDO0ma+sXciAaBl6TY7qMoXPU1aidiC9ExlkjEcjt6uDaj0mJIhKFQ9d646+8ylFh9sO" +
            "kejRlN6d6H4MFl0DgXUs5SNLh+CIXaLiKp3nkWCXv5fb4TeXG3oxLh90XHVo9y4Xsqeu+pDB0443kPq2kuIHHOLw2DKDfwfXSvXY" +
            "aKao5BQlY+J6wb3SdT0eD+bWSkNlt+dyEqDJmA7uV4KdcxtHbvxl5mgUbLNe/ZQ+G80+CnN8GmZ1SJN5T1aCLrG5Cn2U7D0nJ+iS" +
            "ao/qTO/+M2L1lRcxxLKyBPo75Gtdz3X0b/nxqdrNg9hOQB7CKx7uIFW3aSvq6/HzVtu5BK62wDGkAOQINCbCGuQG4u6IGIXbvNKD" +
            "45CQnIo7SXRH+epIw3poAs+weQGzurxmeU0ED0YHS+DouOYTkaBJe2sU9E1+HWqs+9F1mdo9HEzgIS1Dhh+TCV166aAvXWBm+NNS" +
            "jBehuySEsoXDET3R9xDuZ46rv+u6L3/y9q8Le9wecNoJBinLyN81VTou30oYFnTNX2iyhe/gxJCKOLNOIerVJYvOYXdB29uFkupJ" +
            "pNaC2R70b7GVvHs0m6AQ+SdKBNs9AWVMbDl0fbqZUDmoaejdXROWgUao4Nw1Z5IPr6FPIMZkWfGZfQFtD12oDuuTQVWj2o+3YWE/" +
            "XiuTVjaLQa5mARJk+icOkLKH3pO/UEJOY7qLT3Naj55Jm07uU/i3K3X0VFJY3YhaIYWc1DW1Xrj4VSiht70bz5cYy11ovjZobdpB" +
            "+M49fH8cj9nS7yXUO7Ht10/vxPRzHwE5FobELW45tJZkdvj/5+L/JYbiWbFjj50ddPYcEcuj7+AYU70gtIXnwUVaYNIG+tG+Xval" +
            "nmN6Dw7rtovEDakyjZ3e58riwYhVblOwsZje3kN3+M8kB/iqz8v677yq4u2NPH4Ac/CZufaSYi5RyFwltnoOrM6ciAGkuySNgWdI" +
            "7vRp7mrVSFqh5fvSrt5+VC2qMdpPlAk2fp6b5OJ8QDOi8eIAvzaRReAhkqE9FgEHca2IY81r2L7rxbNrVE3AF5AxLj86gf1pmHcn" +
            "LNVth8mpFzXSajnOqV0c/zkbgdTGvhq93RcceRjsZzn4pFmDyRwjFHUPrpTa0IyMPR1xei36dQW0DyKqjJhE7MAzGmEfEypM+2uy" +
            "FTQZzvW4E83kalw3E7kZU07j9Wq7y6U8RVns8iIdmMPNbpKBQ6DRkmk6Njq1fcch8yWF6IQoTNJ732D9B12XkM3naH1oPpCiow3U" +
            "KjfMsIxQJiTF50PmwVM4lkrfuVKJ/SqNJrHLLcNyAPo155caOk/XATz7qeQBJKH0CC8mT0c7ya3wHIyzqWysKEeA2hQ1Go6I7oHD" +
            "Smvx/mz6Fw1qmUrr9ZjRb3EywgxuTBwIAs0UKzKu5hYTKqYhqLqqYmoGKScSRhk9EkBQaxh7IMeRn1qhVXMnI4EiWmZ6JxbZBknt" +
            "h25HmtQ2G31Sgp/CCanzI+Os6HkWeyA3keU0kmIjemWKJw1kmUfn5LqpfvK1hAjMma/n6YMZWud7w2mv7YcU/kBCQMa9EerSdpEx" +
            "NPpovnTIwmGYBL0evqCciyTh9YxCjkgVyJvKtTqOxzNy36fYBkZcONyDO5uWwb3dFr4nU7xiMxac6ePgqLF8CRh8Ejj2lC6X6ABc" +
            "SYLCkWYexYut73B3D4Qti5q9N7BJojcg4q/xxEczB2k6xaF3/FPd32kJR518uV9KNy4JHAWdEx3o3Kgx9DYaiTkPjF41wPEpjZyB" +
            "P5PgphnYXE9TEkGhdF77sqOifNKZVg/FgKpx5H6YFHLCDGmACKJVXlzD24WRuT4eJYFNp5GlVfnUpSkdUO69GgOxI4GoWpzomOb2" +
            "n0dTnK5ZyIckDxqP4Y6kKwC4Xx7o/OzQAK7z2BvJGbU+8BwO49dJ1xIoPX3ABbto20KrxcsIAYkzXFolYlHBlW6HvQYPgo8j6yWp" +
            "fmAdKGd/JlGvIovoXEYA6aB3I+EoLH0aS7u9Bs/h0oOT4Bhex2IfGJ2/c8S9Ih4ZoW9kMTCw+cRuGoxZRu/DWMs4AYY/ZeTgTeiH" +
            "IZK1GeoN214WN+2emDi/g48FYUTluFqqaWIdGcikQlLg2Pmy9OQB7JA9HrziNZG6cfTYx8GE2+bI3BIl0Xn8Pgr+6uXvJ2n8QCYs" +
            "y+yWjg35GVvQlZ4yeQ3TN/a/ubaJvL0Kp78fIEJyCPYjOquNqE+n7Fk/EKyMvoR/M9itF5Kl+vPW5CWKBxi5za9A3A/Lkw92BYtQ" +
            "5GZdmTdOQxnCtlGWOGj8+jNhkgy3wA9VzLgsdpv19buxyPuvJWj2FjUQjrLJJKqt0o7xMv6NZPUuK7A3UxvhGV9W6M/ncFIX3ySk" +
            "Ul048/WqXc+zj2QIzJms6W7Y4GPocsc0iai04lm+Q5qANsJ8vLzkSNAutNhiyR9EwbQF1/T0JNRHeiPlRHobDW46ggYBPJSpfjUP" +
            "PDMPr7tWb6Vdfv82EsC8jIZT5aX7mA3PBWVwPcFyig+PbBaFD4ARoMh5vZqLljAS3BWj/+Xyp1cgLhWaj531llf3sW5QWWUHtd9F" +
            "aJr0MnGIeWAvgQ9bvMbkLexHKSRd2mo67Nj6EW+fGaOyBhuSr636jo7/fTToVZ/wCF+XNhziGwen29tUL2CfbdI9v7ORqtKgdKgO" +
            "6vAvLXaJYxKDnaCQFZQHIt/pVGAtLfT+GsU2DCuOEq452KBOMdaP3zauWKF3Nb3Npm63If+S65XIvD0KJw7yYJQ8W5julIWAbRPJ" +
            "BbUWjqXLTOzS7g/1AO6ELkvZTTi1YOvBx5ImNIFl4Lo1SCcWPpOuFoio+tsoCYjlC95vf+Svmxdyo2tIlknYpN9fd0EA6aQdfFZz" +
            "eLf8etvOstkrYNWdA9qFqogBoE7kStSHpR+epbUZ6jXmVVEYnuNLILX/0P6dbnAIWDjkF5iXjNleprGC9VvAXlKiYh72IqyQqZR1" +
            "PpcZTQrPPHkZdxBsrJPIzKeC9EeZBnkLf0HBKgE+rs5xi0EuTVKA/Sfn5nYIDCCUfDFddndNpHJhaQkctvSNarbr0aZN+gBPwOyU" +
            "qJ93ZoP8oHzPpzHwYGKcw6EKZOhr6Gqxt8DC2m1YhfR1+L0MqMXcji/g4ShvNQIrkRzyDL+3iyCV+tQ/Mt0nIR8GG0yNPEDD6/nE" +
            "EkUE+jhHdPdE5Oj/6+GSXHt0Tn75gm2xuHFnu6gixa1A8OwoxpMHkibN2+z04qtICMXJ5BDfD2Z0poUlenKdT5uZLBQQrHHwXdPV" +
            "CqKyAHo4G1GWcg0fg5GiRfhTyT51FILc3SqPHkwVSLszdhJxK+tCsQFtDKkyenfH0rFKLjX0+y0N1MJCAgj6MV8Yh5GiXa22ewCD" +
            "Om0XXh2RS/cwWMHZvDaeg8oQLShVziuMj5WZr3sj+IxA0toVBAmpmsE4ADo583kLY7ZjZMIakJL6Ebs9lxdoJe5LrHze+qORi56X" +
            "3ooWtE+XUqouvUyCKbE33+ILJ20ywsdkD0BfKuNmd0HmahcEma45xCcm2h8T28Cw3cE2m0TkmplMbSfBmVLcQbcRqypn+KlriNl4" +
            "aeTPNFkgZRGGwG6cNXAyiMtA3d688hQ2YnCg99OeV2QOGiU1t4fSs8jJaS7iFpCLkBGRtHoxUFt9CaeAB8hGYLSLVCqQQHTlN7m3" +
            "2UkHkgv4cu1HKU0FyBYob/Te2yurOQJf1I2esfR+GI91S99iWo7cB1aEGX30M3y4ro8+4CLm1hX/9f2fbOrfH/BahdwXXAZ8vOxz" +
            "y07OdSlIRcEX1fikIIR1VtZ3HZ53yy6n8HoJLD60iWEk3DwrJt/mPZ348v+/tbUcuGe6J9fBT17zkpeu0RqMLkcVRl8giKHdcaUM" +
            "6Mthmf7/jrXuAva7z+QpSwjBPbq6P9uBT452hb/0OlkTIxOs9Lo/1ZEX3/DIpRx8f1grL3fDz627VUWtL/Ev39clRh9NVoW/Fx/o" +
            "DauYGD0L1afm3je/jr1F6HfZBEGGvH/0slmDCewuL5jfIfBTQzvBWOR8nxOP/RQzovYD2a03AUQw3FATTAromO+yZksX8D+FL0/U" +
            "r0nG9CAvptWls86nW0thJgGvrQWPKj6Fy+HOUu4iT6HahB4iY0ybAV8bgV3a/ZMRBVY02ZrPY2+yCteiB/iR72mGXIWp2DBvuTUY" +
            "nc1uj/s5E1MD36/WZkyV2EKiv+BQ06cdOyBUg4QGJ0HLJOd0SfcwwajE9CA0Uztpdtbw1Dq2cuRRUYIIEqRp/7M5IW1puirxnRcb" +
            "4eucpnk1QETS37nGrrcgyyOkeT1JqnYUrZNssTiDPK/j4DVSiVonM+GQnl99FA9UMkFpuRZT4BeGn0/RKSwfAQFPuNr9Mt0fYuRO" +
            "L4GVTaGLeCWIIGmHihoS3IAlwUfWZ3dLwPkSSKC8B/INGLeRx5L38JvIXE0/xC2WvOJglNfLzs7+eT5ACWIMHdGJ2rCcCry859/P" +
            "RORIJ6SvT7Q+h+nBSdx7eh0NEZyPIuJzYuahc0lEqqtmk8WBxNMrmvFeLkc1d0zbZH+9FogH4YPd8HITHZHB3Tc8iLeh4NyKXoGk" +
            "yIzs8R6BkYi4TjWeC/aK36bWx0/rNkK0pyr4jO48Ukz9rrUKhvfXRuTkFjRyvb/gPSFweko1iEyZM0G3337oxPx8igFQ/kAJLF5X" +
            "cji/E4FIv8WvT3E6kcIF5MMih9GVmWLwH+puw1l5T9XB7+OBrdGEcgd/iu6O8T0aCQhh+iBwU0K7U6kRiLRxGV+gG8i0Q8vodE67" +
            "jo+5eiv88E/qxsO+WDSvUEq1LZPrTSfK58FCrfZvlnnRjt42HR+YoTnPPRQPksSRXLH5I8IOdTaZ29iOQ6fQOJ0EtRDDvm4rKf30" +
            "EiHrei67MEWbuPIPEAPZix/74AiS8oDPnW6D1HA39BIh7V5yk+9lLVse+sek28rVeVvf9iKteFeB2JeHwkOjenR+/9bPT3w5BVW8" +
            "7usm3WDmH19VM4bD5Mm6L4d21eQxL2TUsRGT69yOI+GAnBTVRWNPWhGP6y6PXx0rVXoWt6LTICnkHGybHR+XkjKlT4HSTilyABWI" +
            "oKOb5N6/M+zqX+GiIhbEWe5hpk1LyKSkNtGjImBpBorqK1Sqo/QsZEPozq7fTk0txoxQM5Hlm8oEHjJ9HPfcj1f0f0+2WoBw/I6n" +
            "wg+rn8Jry97OcpdT7vSmRJgx7aL5Ek5Oal3Oe1aInKF6JKkGOQxwMasE6Pfn6YpPb7guh7EYWj4vj809Hvb0KD5znIAuxkie1TSM" +
            "jikMbfkLj0M9CAED9I/0ViYYPyBfdFP3ej61RCD2pMeQJ7etnPJ5X9/FEUvgI9uJ9C8Xqo9LaOJBHwW4Bvlv3v88iDParG+9LwEZ" +
            "L77EfoGscGwyySlfPOj75vR+GK+NoNoEH2z6PPPonKeH8/Se6t9khQKmmg6O1VJ96hjEIC0iproq+zkZe1B3l7dyNROwjF7TcjTz" +
            "D2TLqjY58efU1Dg+4kaj/3/chIuwd5yoegZ/t7tF4+3WqYrhk3osWfXk4yD6ScPvScH4AMzLUob7QS3QfTGmz772mtsiw9g0WYOp" +
            "nCUYdR+tlG6Nn3apZaOaLy2HD1SmQrUFy5l0rl/xJ6EKsTq+WDbj1z7f6q37eU/dzKAHM5EpAuJA6xgJxFcmNdiQaRXhR2A7n6G6" +
            "u29QQSs3HowZ1Mo6Rq/txPZTz8yehrIRpcqs/h4yQCUj4o1LtO5b/Hr+8mKavdydBlTZdRO7xSnh+r3q8Syt9U55bSMBi9t5zyWc" +
            "RTyn6eF32PmwuW80R0POPRtS2nQOKt91GHwqJ5jSzNs6g9+DXjUeTRHY2MmA0kXsyDyPgZF53fxeieviV6zWtJV767HHktTyLD6p" +
            "VIqF5P+qqrmOkknn1WxN5uvbHiIXT9XoXCzF9H1+lRZNycjoyCau/vn6kMi+ZDl1uZQFI1A0MHmnUohlhNbIotQhbUPHSDH0lzqm" +
            "PQ5QNSKxbRdSQ5lIuBT0d/L7/Jr4q+jya5yfoZ6l0MlP1tFK2HI7JmS9XvcYwckjWhy6l3J8evW4DCD/NQXqfedSqVva/6oR6M/t" +
            "YoPl9rEE5TvVWLQRobKPG+FkgGoikoHFu+H9NJrueUqu2Vh7Cq70vV/B84ncILzmg0/yPUKn+KpBLriejYRiERPwp5XmOQ5z8R5Q" +
            "afQ3nGZuJRRPnHX0XvvRSF+O5D4hOy5vmLSSIVWXEOGmOuReG18vBYCQnpDBR+7EVG0vXIi+ohKeI5E0UiQAU2H8t4P2uzb0avgN" +
            "YEpPw0pJXUCSi2/CZ04Vshq9O+FrnAL0FlhTOQ1xCHqpaTWMQlKgec6uMs/1v5aztFocnf0l6ncShR/jaSQTbt59f6jGbnpdZ72v" +
            "HkCk1+Bw3CE6KfpwGfaHI+yikPYQ3NG8b9r3rrxrono/uvVe5DYjCA8gDHo8HzV8hbuhR5S9ej6sBLo/9NQXmdRpRQUcSvkaFwWX" +
            "R+voOMwa2E8abA9zViLMrJfReFKC9E+T/QdXmORDxAObm4SeJrUB71ViRA96DCnu/lsJ/7HXkH5T6GknOgQfxfkVu5hCS/MRx8Hz" +
            "3AU9DN9RRKNIMqO0bEwtUd5EMkHuRN6DotQ2GTy1vcVtwqe0Qsx1dGkcTT2ohCduVCU4peU2BoxVGBZCb1UBdjYJDCvNlagXCwZk" +
            "rsUpSPaIXfoGemC4WmLiUxwjYjq3olup9HIU/7h9H+XUBzg20lynEdh3IL21Cjwv9s4xwvRN5CHhyIiiCuRqX3K9A8mRnI2y2/Ln" +
            "G4+usoNBcXbHwa+BM6G3bep2hFQMotr1KN7RyIHrQ+dINPIHHbn0YX/+no9wMYXq4nKXO9MNq/ePD4cdnrmnkYzTyUWowET6URk0" +
            "msxmfQNYtzP/WSj9UDbzmjSKqw6r2nFtPJlz6SMNRG4O9aeG8JWaxzSe7hhGIR5s+B0b2wo2YCvVWrfDsa3Geh+3YSlYKwCIWe7k" +
            "Nhx3hN8JvQuZ+T4jNWomt/EcoB/hVDc5ut8ipajzS0wkGoK/KtaH7S42XHuhLlQo5C99oMFLL+BRLXm4F/Y2Qsw7vP0IqAPFf2c/" +
            "UAMR/d8KNRJce5aPCJhWIllQ9ePKgOV3ZpI7qBXoasrTgcsZzKSqNdJN7IGIbmOEaTWNa7qVzpLKY6J9BDvg9Vu8wgEYp1VBYO1M" +
            "pjFEnO33h0jctnv8+v877yfl5za/w/q7W6GxGHZaYjj6J8n04jqby6Bg2o5dQWnFIJxoymsOSIetVXc5HR0gp9yFuaRaXwxUxFon" +
            "EvqsaagWL8o9D1uRblMMY1+YzlKGGeRcucbrKvvqrFGCR6x6GCneXomR1A3sm90Xkbh+7nW5CHvb/2k8uVVgSkvNLltKr/HUySeI" +
            "xv9t3IrRyFbvi4HQjIzS239IeDy5GAHF72t2uobI0yiOKmRyPrbC6qeomZRzLYPkkyIA2SCOLUqs9dRP21C0YCu6NzMBZZuuWlyS" +
            "fWeH0JPbSnIq/0j1EJ7mB0bv6mzucsL/v5hSikEFfsvZv6nVKz5DE0mB8c7f8NZf87hqRK6ldV7zsg2sduNOHykSFbrt/C5BW0fv" +
            "0nIYGLk+a1MvPnomqy60gWQnoREoaror+/nKFivoOku8E9ZMfJtDZ5r11moGt5NnoWNyEj9wkkiHcj47DVKjLTAq1MJLybZPb3Se" +
            "gGnIMevA+XvS5eyes5knLKBWiyzgyUi/hbkkFquCqZfkZl1VKJyvBVzE+j7wUUMz0FlUieFP0eeyC/IPE21pZtewmyXo9CEyc/x8" +
            "gOYT2FLDWQQP4puk4vRdc1PsZyL+u7ZT+/C3md16AQwsnUrrJ6CK0SBxKam5ClfAvwRZLJlnnyo+h7F5qrdAm6tudQ2VbnF1XvOx" +
            "DNb/kEtZoDFov1ch9dwBsC9rMXXYu4jUitacwTUJntRWgQfStKhh+HZu6vIJnn8zzKp7wXJeJfQ7biQbQvnVgie3R0ruahKMf3UO" +
            "HOz7F45E4rF3wnmjEcP+gfRDXWD5D0LvohyaScPpKEXPzArkPW0RUkD0V5f6PRdX6GyqRsK5VCMU9QaXE+RuWExpgvoUENVPZ3B/" +
            "JK7iJpIXIvycxlUE4lPu4eNOHwIfTQPkuSlJ1MesotxwllP5d7jdWWbYFkhu4khoYIy19fXq4ctw7pQgn0tSgufjmJ9VteOnk1Es" +
            "mnot+PR9bvKuDt1BaQPWiy6W1IUA9AieGT0H31E2oTH3uBoX21QPdFtZVd6zhBg0rc7+gIZCysR4nW2Pv4RXTs9ag8p8USTBwPE8" +
            "bXapp3GmGtS0D33pTomOtNVh0bfca5JDP5SyiMuAWtTvh6JCoXoNZBjwfuTyMOQLmJ4WIXSV+3D6Lx5zA0cfY6hrfh6n5Nq1VYP0" +
            "E367vRxKipyEJdh5T/C1TOPfh3NCi9E91kT6MJh19FVSaTqIy53wR8IPr5xqrPvr3sf6HW040kPXp+Ru0bbTsaDH8PWeFzkJjFHV" +
            "6vR7O6q+cDvA9ZjK+MjrUfWeUfQgPlXBTySttVbW3Z8a4u+/uysr9XT6DrR7H6qdGxVVv1XyZZTe7Bsr9/AQ2074je+0x0jF9Fwj" +
            "mRylAeyCL/Khp4JyIxuQtZgmPqHNPDyDo+lmSmczwJrN5A+zk0UJSonJfwyWgbgyiEUX2cv4l+fqDs70Xg96Nz8AaSRY72oGt7VX" +
            "Tc1d2HNwHvR+JR2V5+YIDCIbMozDoI+obo5nx0T9edfNiA7ui41iOhmBh9xZViz0X7vRUZME+je2ZN9L5nGD7Pdz66n7LqJVVA9/" +
            "JzJC1aNqN7bFV0jJuo7ZmZYaRQStGjZdG8+fX+NQHdpM0qGwrIKhyOMEUjvoLEDBS+SJM8jBPn/aS/YSeS5ID2NibQ3nWK28X0ok" +
            "E8rQX+PZT4BXl6w7GUW3xt+wi1Wvf0UThyEV0fek+zVQjN/si4sRT/4+uUrv8ljMu+lubx1ava30gbtDsPJO1AU2rhtXkxhSQEtY" +
            "qhidJ67KH1wWVvrvhIc50WopABJL2TYspH0fLJaGOQ19qLwiu/qdpmuWfW/opw6Qi5tsaYiE4kvTrFmSTrQ1yL68Hb4XXoHF6Lwp" +
            "nlzCMxTMrDXhNQEvtalAPrrfE+kDdQHZIyxoxA9r32kPUpX5/gx53emb2c8jzAH6Mc2GMoJ/FRkkTzz8te9wyqCjoHJXz/ExUelF" +
            "Cpa7x63VIqcz7GmBHK/iIgY1B57RYUvrqt0zu0l3MzGvzfhLy6b9R4zeVUtkEpoYKCK1CO6N0M9V62oGR1J1vkG2NSsr8ISD+aRF" +
            "hAoavhirHvqwygeQfXooq1uAX+IBLoG9B8oGohuBF5IG9GEwdHk1QU3Re95+FOH5wxJh2pqrCMMcaYavanJLoxxpgMsYAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCAsIMYYY4KwgBhjjAnCAmKMMSYIC4gxxpggLCDGGGOCsIAYY4wJwg" +
            "JijDEmCAuIMcaYICwgxhhjgrCAGGOMCcICYowxJggLiDHGmCD+P/kqvarGLQw5AAAAInpUWHRTb2Z0d2FyZQAAeNpzTMlPSlXwzE" +
            "1MTw1KTUypBAAvnAXUrgypTQAAAABJRU5ErkJggg==";


    @Before
    public void setUp() throws IOException {
        client = new UploadClient(APIClientConfig.load());
    }

    @Test
    public void testUploadImage() throws IOException {
        File f = createTestFile();
        String result = client.uploadForProcessing(f);
        Assert.assertTrue(result.contains("png"));
        f.delete();
    }

    public static File createTestFile() throws IOException {
        Base64 decoder = new Base64();
        byte[] imgBytes = decoder.decode(UploadClientIntegrationTest.TEST_IMAGE);
        File f = File.createTempFile("imagga", ".png");
        FileOutputStream osf = new FileOutputStream(f);
        osf.write(imgBytes);
        osf.flush();
        return f;
    }

}

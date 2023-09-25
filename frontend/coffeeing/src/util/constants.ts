export const API_URL = process.env.REACT_APP_BASE_API_URL;

export type Item = {
    value: number,
    name: string
}

export const GENDER_ITEMS = [
    {
        value:0,
        name: "남성"
    },
    {
        value:1,
        name: "여성"
    },
]

export const AGE_ITEMS = [
    {
        value:0,
        name: "10대"
    },
    {
        value:1,
        name: "20대"
    },
    {
        value:2,
        name: "30대"
    },    {
        value:3,
        name: "40대"
    },    {
        value:4,
        name: "50대"
    },    {
        value:5,
        name: "60대"
    },
];

export const ROAST_ITEMS = [
    {value:0.2, name:'라이트'},
    {value:0.4, name:'미디엄 라이트'},
    {value:0.6, name:'미디엄'},
    {value:0.8, name:'미디엄 다크'},
    {value:1, name:'다크'},
]

export const ACIDITY_ITEMS = [
    {value:0.5, name:'낮음'},
    {value:0.75, name:'중간'},
    {value:1, name:'높음'},
]

export const BODY_ITEMS = [
    {value:0.3, name:'가벼움'},
    {value:0.6, name:'중간'},
    {value:0.9, name:'무거움'},
]

export const FLAVOR_NOTE_ITEMS = [
    {value:1, name:'과일'},
    {value:2, name:'달콤'},
    {value:3, name:'초콜릿'},
    {value:4, name:'견과류'},
    {value:5, name:'매콤함'},
    {value:6, name:'꽃향'},
]
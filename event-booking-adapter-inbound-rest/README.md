# Event Booking APIs

> Version 1.0.0

## Path Table

| Method | Path | Description |
| --- | --- | --- |
| `POST | [/users](#postusers) | Create a new user. |
| POST | [/auth](#postauth) | Authenticate a user. |
| POST | [/events](#postevents) | Create a new event. |
| GET | [/events](#getevents) | Get all events or search for events. |
| POST | [/events/{eventId}/tickets](#posteventseventidtickets) | Reserve tickets for an event. |
| DELETE | [/events/tickets/{ticketId}](#deleteeventsticketsticketid) | Cancel reservation for an event. |
| GET | [/events/tickets](#geteventstickets) | Get all booked events. |

## Reference Table

| Name | Path | Description |
| --- | --- | --- |
| User | [#/components/schemas/User](#componentsschemasuser) |  |
| Credentials | [#/components/schemas/Credentials](#componentsschemascredentials) |  |
| EventRequestDTO | [#/components/schemas/EventRequestDTO](#componentsschemaseventrequestdto) |  |
| EventResponseDTO | [#/components/schemas/EventResponseDTO](#componentsschemaseventresponsedto) |  |
| EventTicketResponseDTO | [#/components/schemas/EventTicketResponseDTO](#componentsschemaseventticketresponsedto) |  |
| TicketRequest | [#/components/schemas/TicketRequest](#componentsschemasticketrequest) |  |
| Category | [#/components/schemas/Category](#componentsschemascategory) |  |

## Path Details

***

### [POST]/users
***Create a new user.***

This endpoint allows customers to create a new user.

#### RequestBody

- application/json

```ts
{
  name: string
  email: string
  password: string
}
```

#### Responses

- 201 Created

- 400 Bad request

***

### [POST]/auth
***Authenticate a user.***

This endpoint allows users to authenticate and receive a Bearer token.

#### RequestBody

- application/json

```ts
{
  email: string
  password: string
}
```

#### Responses

- 200 OK

- 401 Unauthorized

***

### [POST]/events
***Create a new event.***

This endpoint allows customers to create a new event.

#### RequestBody

- application/json

```ts
{
  name: string
  date: string
  availableAttendeesCount: integer
  description: string
  category: [Concert, Conference, Game]
}
```

#### Responses

- 201 Created

`application/json`

```ts
{
  eventId: integer
}
```

- 400 Bad request

- 401 Unauthorized

***

### [GET]/events
***Get all events or search for events.***

This endpoint allows customers to retrieve all events or search for events by name, date range or category.

#### Parameters(Query)

```ts
name: string
```

```ts
startDate: string
```

```ts
endDate: string
```

```ts
category: [Concert, Conference, Game]
```

#### Responses

- 200 OK

`application/json`

```ts
[
    {
      id: integer,
      name: string,
      date: string,
      availableAttendeesCount: integer,
      description: string,
      category: [Concert, Conference, Game]
    }
]
```

- 404 Not found

***

### [POST]/events/{eventId}/tickets
***Reserve tickets for an event.***

This endpoint allows customers to reserve tickets for an event.

#### RequestBody

- application/json

```ts
{
  attendeesCount: integer
}
```

#### Responses

- 201 Created

- 400 Bad request

- 401 Unauthorized

***

### [DELETE]/events/tickets/{ticketId}
***Cancel reservation for an event.***

This endpoint allows customers to cancel ticket for an event.

#### Responses

- 202 No content

- 401 Unauthorized

***

### [GET]/events/tickets
***Get all booked events.***

This endpoint allows customers to retrieve all booked events.

#### Responses

- 200 OK

`application/json`

```ts
[
  {
    ticketId: integer,
    eventId: integer,
    attendanceCount: integer
  }
]
```

- 401 Unauthorized

- 404 Not found

## References

### #/components/schemas/User

```ts
{
  name: string
  email: string
  password: string
}
```

### #/components/schemas/Credentials

```ts
{
  email: string
  password: string
}
```

### #/components/schemas/EventRequestDTO

```ts
{
  name: string
  date: string
  availableAttendeesCount: integer
  description: string
  category: enum[Concert, Conference, Game]
}
```

### #/components/schemas/EventResponseDTO

```ts
{
  id: integer
  name: string
  date: string
  availableAttendeesCount: integer
  description: string
  category: enum[Concert, Conference, Game]
}
```

### #/components/schemas/EventTicketResponseDTO

```ts
{
  ticketId: integer
  eventId: integer
  attendanceCount: integer
}
```

### #/components/schemas/TicketRequest

```ts
{
  attendeesCount: integer
}
```

### #/components/schemas/Category

```ts
{
  "type": "string",
  "enum": [
    "Concert",
    "Conference",
    "Game"
  ]
}
```
identifier(identifier(X)) --> [X], {atom(X)}.
number(number(X)) --> [X], {number(X)}.


assign(assign(X, Y)) --> identifier(X), [:=], expr(Y).

expr(X) --> term(X).
expr(add(X, Y)) --> term(X), [+], expr(Y).
expr(sub(X, Y)) --> term(X), [-], expr(Y).

term(mul(X, Y)) --> factor(X), [*], term(Y).
term(divv(X, Y)) --> factor(X), [/], term(Y).
term(X) --> factor(X).

factor(X) --> ['('], expr(X), [')'].
factor(X) --> number(X).


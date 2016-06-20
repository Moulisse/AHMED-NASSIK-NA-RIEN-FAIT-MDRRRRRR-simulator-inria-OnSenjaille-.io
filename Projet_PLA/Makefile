main: a.cmo main.ml
	ocamlc a.cmo main.ml -o main




a.cmo: a.cmi a.ml
	ocamlc -c a.ml
a.cmi: a.mli
	ocamlc -c a.mli

clean:
	rm *.cmo *.cmi 
